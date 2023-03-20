package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.*;
import com.epam.spring.testingapp.model.*;
import com.epam.spring.testingapp.repository.*;
import com.epam.spring.testingapp.service.RunningTestService;
import com.epam.spring.testingapp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RunningTestServiceImpl implements RunningTestService {
    private final TestRepository testRepository;
    private final AccountRepository accountRepository;
    private final RunningTestRepository runningTestRepository;
    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;
    private final TestResultService testResultService;

    @Override
    public RunningTest start(int testId, int accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id %s not exist".formatted(accountId)));
//      Finish other test that can running for this account
        finishAllRunningTestForAccount(accountId);

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));

        log.debug("Creating running test instance...");
        RunningTest runningTest = RunningTest.builder()
                .test(test)
                .account(account)
                .startTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        runningTest = runningTestRepository.save(runningTest);
        log.debug("Test#{}#{} for account#{} was started!", test.getId(), test.getName(), accountId);
        return runningTest;
    }

    @Override
    public RunningTest addUserAnswer(Set<Integer> answersIds, int accountId) {
        RunningTest runningTest = runningTestRepository.findFirstByAccount_IdAndTestResultNull(accountId).orElseThrow(() ->
                new NoTestRunningForAccountException("Currently for account with id %s no test is running".formatted(accountId)));
        log.info("Current {}", runningTest);

        LocalDateTime finishTime = runningTest.getStartTime().toLocalDateTime().plusMinutes(runningTest.getTest().getDuration());

        if(LocalDateTime.now().isAfter(finishTime)){
            log.debug("The runningTest#{} time is up!", runningTest.getId());
            throw new TestTimeIsUpException("The runningTest#%s time is up. You cant add answers to it.".formatted(runningTest.getId()));
        }

        Set<Answer> savedAnswers = runningTest.getUserAnswers();
        if(savedAnswers == null){
            savedAnswers = new HashSet<>();
            runningTest.setUserAnswers(savedAnswers);
        }

//      Iterating throw set and validating answersIds than converting in Answer entities
        Set<Answer> userAnswers = answersIds.stream().map(answerId -> {
            log.debug("Start converting answerId-{} into answer", answerId);
            Answer answer = answerRepository.findById(answerId)
                    .orElseThrow(() -> new UnprocessableEntityException("Answer with id %s doesn't exist. Not possible to add it as answer for question.".formatted(answerId)));
            log.debug("Founded -> {}", answer);
            if (!answer.getQuestion().getTest().equals(runningTest.getTest()))
                throw new UnprocessableEntityException("Answer with id %s not relevant to test#%s".formatted(answerId, runningTest.getTest().getId()));
            return answer;
        }).collect(Collectors.toSet());

        Set<Question> questionsToResetAnswer = userAnswers.stream().map(Answer::getQuestion).collect(Collectors.toSet());

//      removing all saved answers that is from questions of our user new answers
        savedAnswers = savedAnswers.stream().filter(answer -> !questionsToResetAnswer.contains(answer.getQuestion())).collect(Collectors.toSet());
//      adding user new answers
        savedAnswers.addAll(userAnswers);

        runningTest.setUserAnswers(savedAnswers);

        runningTestRepository.save(runningTest);
        log.info("Saved answersIds: {}", runningTest.getUserAnswers());

        return runningTest;
    }

    @Override
    public Page<Question> getQuestion(Pageable pageable, Integer accountId) {
        log.debug("Start getting question from running test by sequence number...");
        RunningTest runningTest = runningTestRepository.findFirstByAccount_IdAndTestResultNull(accountId).orElseThrow(() ->
                new NoTestRunningForAccountException("Currently for account with id %s no test is running".formatted(accountId)));
//        Question question;
//        try{
//            question = runningTest.getTest().getQuestions().get(pageable);
//        }catch (IndexOutOfBoundsException e){
//            log.debug("Sequence number is out of bounds!", e);
//            throw new SequenceNumberOutOfBoundsException("There are no questions for sequenceNumber = %s "
//                    .formatted(pageable));
//        }

//        log.info("Founded question of sequence number({}) = {}", pageable, question);
        return questionRepository.findAllByTestId(runningTest.getTest().getId(), pageable);
    }

    @Override
    public TestResult finish(int accountId) {
        log.info("Finishing current test for account#{}...", accountId);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id %s not found".formatted(accountId)));

//      get current running test.
        RunningTest runningTest = runningTestRepository.findFirstByAccount_IdAndTestResultNull(account.getId()).orElseThrow(() ->
                new NoTestRunningForAccountException("Currently for account with id %s no test is running".formatted(accountId)));

        Test currentTest = runningTest.getTest();

//      creating test result
        TestResult testResult = testResultService.save(runningTest);

//      adding results to running test.
        runningTestRepository.save(runningTest);

        log.info("Test#{}#{} passed with result = {}", currentTest.getId(), currentTest.getName(), testResult);
        return testResult;
    }

    @Override
    public RunningTest findCurrent(int accountId) {
        log.debug("Start getting running test by accountId...");
        RunningTest runningTest = runningTestRepository.findFirstByAccount_IdAndTestResultNull(accountId).orElseThrow(() ->
                new NoTestRunningForAccountException("Currently for account with id %s no test is running".formatted(accountId)));

        return runningTest;
    }

    private void finishAllRunningTestForAccount(int accountId) {
        log.debug("Finishing all previously started tests for account#{}", accountId);
        List<RunningTest> allNotFinished = runningTestRepository.findAllNotFinished(accountId);

        for (RunningTest runningTest : allNotFinished) {
            log.debug("Stopping {}", runningTest);
            //      creating test result
            TestResult testResult = testResultService.save(runningTest);
            //      saving finished runningTest to running test.
            runningTestRepository.save(runningTest);
            log.debug("Result for closed test = {}", testResult);
        }
        log.debug("All tests that was running for account#{} was stopped", accountId);
    }
}
