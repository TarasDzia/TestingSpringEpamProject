package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.*;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.mapper.TestResultMapper;
import com.epam.spring.testingapp.model.*;
import com.epam.spring.testingapp.repository.AccountRepository;
import com.epam.spring.testingapp.repository.TestRepository;
import com.epam.spring.testingapp.repository.TestResultRepository;
import com.epam.spring.testingapp.service.QuestionService;
import com.epam.spring.testingapp.service.TestResultService;
import com.epam.spring.testingapp.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestResultServiceImpl implements TestResultService {
    private final TestRepository testRepository;
    private final TestResultRepository testResultRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<TestResultDto> findAllByAccount(int accountId) {
        List<TestResult> testResults = testResultRepository.findByAccount_Id(accountId);

        log.info("Founded testResults of account#{} = {}", accountId, testResults);
        return TestResultMapper.INSTANCE.testResultsToTestResultsDtos(testResults);
    }

    @Override
    public TestResultDto passTest(Set<UserAnswerDto> userAnswers, int testId, int accountId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not found".formatted(testId)));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id %s not found".formatted(accountId)));

        List<Question> questions = test.getQuestions();

        int countOfQuestions = questions.size();
        int countOfCorrectAnswers = 0;

        for (UserAnswerDto userAnswer : userAnswers) {
//          Founding the question of this user answer.
            Question question = questions.stream()
                    .filter(entity -> entity.getId().equals(userAnswer.getQuestionId()))
                    .findFirst().orElse(null);

            if(question == null || question.getAnswers() == null)
                continue;

//          Founding expected correct answers ids of this question
            Set<Integer> correctAnswersIds = question.getAnswers().stream().filter(Answer::isCorrect).map(Answer::getId).collect(Collectors.toSet());
//          Getting user answers
            Set<Integer> userChosenAnswers = userAnswer.getAnswerIds();

            if (correctAnswersIds.equals(userChosenAnswers)){
                countOfCorrectAnswers++;
            }
        }
        int scores = (int) Math.round((double)countOfCorrectAnswers / ((double) countOfQuestions) *100);


//      creating test result
        TestResult testResult = TestResult.builder()
                .account(account)
                .test(test)
                .score(scores)
                .completionDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        testResult = testResultRepository.save(testResult);
        log.info("Test#{} passed with result = {}", testId, testResult);
        return TestResultMapper.INSTANCE.testResultToTestResultDto(testResult);
    }
}
