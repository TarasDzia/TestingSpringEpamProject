package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.*;
import com.epam.spring.testingapp.exception.NoTestRunningForAccountException;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.SequenceNumberOutOfBoundsException;
import com.epam.spring.testingapp.mapper.QuestionMapper;
import com.epam.spring.testingapp.mapper.UserAnswerMapper;
import com.epam.spring.testingapp.model.*;
import com.epam.spring.testingapp.repository.AccountRepository;
import com.epam.spring.testingapp.repository.RunningTestRepository;
import com.epam.spring.testingapp.repository.TestRepository;
import com.epam.spring.testingapp.repository.UserAnswerRepository;
import com.epam.spring.testingapp.service.TestPassingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestPassingServiceImpl implements TestPassingService {
    private final TestRepository testRepository;
    private final AccountRepository accountRepository;
    private final RunningTestRepository runningTestRepository;
    private final UserAnswerRepository userAnswerRepository;

    @Override
    public void startTest(Integer testId, Integer accountId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id %s not exist".formatted(accountId)));

        RunningTest runningTest = RunningTest.builder()
                .test(test)
                .account(account)
                .startTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        runningTestRepository.save(runningTest);
    }

    @Override
    @Transactional
    public UserAnswerDto addUserAnswer(UserAnswerDto userAnswerDto, int accountId) {
        RunningTest runningTest = runningTestRepository.findByAccountNotFinished(accountId).orElseThrow(() ->
                new NoTestRunningForAccountException("Currently for account with id %s no test is running".formatted(accountId)));
        log.info("Current test {}", runningTest);

        UserAnswer userAnswer = UserAnswerMapper.INSTANCE.toEntity(userAnswerDto);
        userAnswer.setRunningTest(runningTest);

        // TODO: 23.11.2022  check if answers exists in this question. check if user answer for this question already exist if so just update it.
        // TODO: 23.11.2022 catch SQLIntegrityConstraintViolationException with message answer or question id is bad, unprocessable entity exception
        // TODO: 23.11.2022 choose answer save only one from array. Fix it by using named query or google it too find out.
        userAnswer = userAnswerRepository.save(userAnswer);
        log.info("Saved {}", userAnswer);

        return UserAnswerMapper.INSTANCE.toDto(userAnswer);
    }

    public QuestionDto getQuestion(Integer sequenceNumber, Integer accountId) {
        RunningTest runningTest = runningTestRepository.findByAccountNotFinished(accountId).orElseThrow(() ->
                new NoTestRunningForAccountException("Currently for account with id %s no test is running".formatted(accountId)));

        Question question;
        try{
            question = runningTest.getTest().getQuestions().get(sequenceNumber);
        }catch (IndexOutOfBoundsException e){
            throw new SequenceNumberOutOfBoundsException("There are no questions for sequenceNumber = %s "
                    .formatted(sequenceNumber));
        }

        log.info("Founded question = {}", question);
        return QuestionMapper.INSTANCE.mapDtoWithoutCorrect(question);
    }
}
