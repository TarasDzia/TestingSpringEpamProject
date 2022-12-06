package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.*;
import com.epam.spring.testingapp.mapper.TestResultMapper;
import com.epam.spring.testingapp.model.*;
import com.epam.spring.testingapp.repository.AccountRepository;
import com.epam.spring.testingapp.repository.AnswerRepository;
import com.epam.spring.testingapp.repository.RunningTestRepository;
import com.epam.spring.testingapp.repository.TestRepository;
import com.epam.spring.testingapp.service.RunningTestService;
import com.epam.spring.testingapp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final TestResultService testResultService;

    @Override
    public RunningTest startTest(int testId, int accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id %s not exist".formatted(accountId)));
//      Finish other test that can running for this account
        finishAllRunningTestForAccount(accountId);

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));

        RunningTest runningTest = RunningTest.builder()
                .test(test)
                .account(account)
                .startTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        runningTestRepository.save(runningTest);
        return runningTest;
    }

    @Override
    public RunningTest addUserAnswer(Set<Integer> answersIds, int accountId) {
        RunningTest runningTest = runningTestRepository.findFirstByAccount_IdAndTestResultNull(accountId).orElseThrow(() ->
                new NoTestRunningForAccountException("Currently for account with id %s no test is running".formatted(accountId)));
        log.info("Current {}", runningTest);

        try {
            finishOverDueTest(runningTest);
        } catch (TestTimeIsUpException e) {
            log.info(e.getMessage());
        }

        Set<Answer> savedAnswers = runningTest.getUserAnswers();

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

    public Question getQuestion(Integer sequenceNumber, Integer accountId) {
        RunningTest runningTest = runningTestRepository.findFirstByAccount_IdAndTestResultNull(accountId).orElseThrow(() ->
                new NoTestRunningForAccountException("Currently for account with id %s no test is running".formatted(accountId)));

        try {
            finishOverDueTest(runningTest);
        } catch (TestTimeIsUpException e) {
            log.info(e.getMessage());
        }

        Question question;
        try{
            question = runningTest.getTest().getQuestions().get(sequenceNumber);
        }catch (IndexOutOfBoundsException e){
            throw new SequenceNumberOutOfBoundsException("There are no questions for sequenceNumber = %s "
                    .formatted(sequenceNumber));
        }

        log.info("Founded question = {}", question);
        return question;
    }

    @Override
    public TestResult finishTestById(int accountId) {
        log.info("Finishing current test for account#{}", accountId);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id %s not found".formatted(accountId)));

//      get current running test.
        RunningTest runningTest = runningTestRepository.findFirstByAccount_IdAndTestResultNull(account.getId()).orElseThrow(() ->
                new NoTestRunningForAccountException("Currently for account with id %s no test is running".formatted(accountId)));

        Test currentTest = runningTest.getTest();
        int score = calculateScoreForRunningTest(runningTest);

//      creating test result
        TestResult testResult = testResultService.saveTestResult(score, runningTest);

//      adding results to running test.
        runningTestRepository.save(runningTest);

        log.info("Test#{}#{} passed with result = {}", currentTest.getId(), currentTest.getName(), testResult);
        return testResult;
    }

    private int calculateScoreForRunningTest(RunningTest runningTest) {
        log.info("Start scoring RunningTest#{} with userAnswers {}", runningTest.getId(), runningTest.getUserAnswers());
        List<Question> questions = runningTest.getTest().getQuestions();

        int countOfQuestions = questions.size();
        int countOfCorrectAnswers = 0;

        for (Question question: questions) {
            log.debug("Answers for question#{} are evaluating...", question.getId());

//          If the answers for these question exists
            if(question.getAnswers() == null){
                log.info("Question doesnt contain any ANSWERS skipping...");
                continue;
            }

//          Founding expected correct answers of this question
            Set<Answer> correctAnswers = question.getAnswers().stream().filter(Answer::isCorrect).collect(Collectors.toSet());
            log.info("Founding expected correct answers of question#{}#{} = {}", question.getId(), question.getDescription(), correctAnswers);
            if (correctAnswers.isEmpty()){
                log.info("Question doesnt contain any CORRECT ANSWERS skipping...");
                continue;
            }

//          Founding user answer for this question
            Set<Answer> userChosenAnswersForCurrentQuestion = runningTest.getUserAnswers()
                    .stream().filter(userChosenAnswers -> userChosenAnswers.getQuestion().getId() == question.getId())
                    .collect(Collectors.toSet());

            log.info("Founding user answer for question#{}#{} = {}", question.getId(), question.getDescription(), userChosenAnswersForCurrentQuestion);

            if (correctAnswers.equals(userChosenAnswersForCurrentQuestion)){
                countOfCorrectAnswers++;
                log.info("Answer for question#{}#{} correct,", question.getId(), question.getDescription());
            }
        }

        return (int) Math.round((double) countOfCorrectAnswers / ((double) countOfQuestions) * 100);
    }
    private void finishAllRunningTestForAccount(int accountId) {
        log.debug("Start closing tests that currently running for account#{}", accountId);
        List<RunningTest> allNotFinished = runningTestRepository.findAllNotFinished(accountId);

        for (RunningTest runningTest : allNotFinished) {
            log.debug("Stopping {}", runningTest);
            //      creating test result
            TestResult testResult = testResultService.saveTestResult(calculateScoreForRunningTest(runningTest), runningTest);
            //      adding results to running test.
            runningTestRepository.save(runningTest);
            log.debug("Result for closed test = {}", testResult);
        }
    }
    private void finishOverDueTest(RunningTest runningTest) throws TestTimeIsUpException {
        LocalDateTime finishTime = runningTest.getStartTime().toLocalDateTime().plusMinutes(runningTest.getTest().getDuration());

        if(LocalDateTime.now().isAfter(finishTime)){
            log.debug("The test time is up {}", runningTest);
            //      creating test result
            TestResult testResult = testResultService.saveTestResult(calculateScoreForRunningTest(runningTest), runningTest);
            //      adding results to running test.
            runningTestRepository.save(runningTest);
            log.debug("Result for closed test = {}", testResult);

            throw new TestTimeIsUpException("The runningTest#%s time is up. For test created result with id %s".formatted(runningTest.getId(), testResult.getId()));
        }
    }
}
