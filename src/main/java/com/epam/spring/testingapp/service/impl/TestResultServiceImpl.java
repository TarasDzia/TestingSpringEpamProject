package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.dto.UserAnswerDto;
import com.epam.spring.testingapp.mapper.TestResultMapper;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.model.TestResult;
import com.epam.spring.testingapp.service.QuestionService;
import com.epam.spring.testingapp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestResultServiceImpl implements TestResultService {
    private final QuestionService questionService;

    @Override
    public List<TestResultDto> findAllByAccount(int accountId) {
        List<TestResult> testResults = List.of(TestResult.builder().id(1).account(Account.builder().id(accountId).build()).build(),
                TestResult.builder().id(2).account(Account.builder().id(accountId).build()).build());
        log.info("Founded testResults of account#{} = {}", accountId, testResults);
        return TestResultMapper.INSTANCE.testResultsToTestResultsDtos(testResults);
    }

    @Override
    public TestResultDto passTest(Set<UserAnswerDto> userAnswers, int testId, int accountId) {
        List<QuestionDto> questions = questionService.findAll(testId);
//        questionDtos.stream().filter(questionDto -> questionDto.)

        int countOfQuestions = questions.size();
        int countOfCorrectAnswers = 0;

        for (UserAnswerDto userAnswer : userAnswers) {
//          Founding the question of this user answer.
            QuestionDto question = questions.stream()
                    .filter(questionDto -> questionDto.getId() == userAnswer.getQuestionId())
                    .findFirst().orElse(null);

            if(question == null || question.getAnswers() == null)
                continue;

//          Founding expected correct answers of this question
            List<AnswerDto> correctAnswers = question.getAnswers().stream().filter(AnswerDto::isCorrect).toList();
            UserAnswerDto expectedUserAnswers = UserAnswerDto.builder().questionId(question.getId())
                    .answerIds(correctAnswers.stream().map(AnswerDto::getId).collect(Collectors.toSet())).build();

            if (expectedUserAnswers.equals(userAnswer)){
                countOfCorrectAnswers++;
            }
        }
        int scores = (int) Math.round((double)countOfCorrectAnswers / ((double) countOfQuestions) *100);


//        saving test result in db
        TestResult testResult = TestResult.builder()
                .id(1)
                .account(Account.builder().id(accountId).build())
                .test(Test.builder().id(testId).build())
                .score(scores)
                .completionDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        log.info("Test#{} passed with result = {}", testId, testResult);
        return TestResultMapper.INSTANCE.testResultToTestResultDto(testResult);
    }
}
