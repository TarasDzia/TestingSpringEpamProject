package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.model.*;
import com.epam.spring.testingapp.repository.TestResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.epam.spring.testingapp.utils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestResultServiceImplTest {
    @Mock
    private TestResultRepository testResultRepository;

    @InjectMocks
    private TestResultServiceImpl testResultService;

    private RunningTest runningTest;
    private Test test;
    private TestResult expectedTestResult;
    private List<TestResult> expectedTestResults;
    private Question question;
    private Answer correctAnswer;
    private Answer badAnswer;
    private Set<Answer> answers;
    private List<Question> questions;

    @BeforeEach
    void setUp() {
        Account account = getAccount();
        test = getTest();
        question = getQuestion(test);
        correctAnswer = getCorrectAnswer(question);
        badAnswer =getBadAnswer(question);
        runningTest = getRunningTest(account, test);
        expectedTestResult = getTestResult(account);

        questions = List.of(question);
        answers = Set.of(correctAnswer, badAnswer);
        expectedTestResults = List.of(expectedTestResult, getTestResult(account));

        test.setQuestions(questions);
        question.setAnswers(answers);

        runningTest.setUserAnswers(answers);
        runningTest.setTestResult(expectedTestResult);
        expectedTestResult.setRunningTest(runningTest);
    }

    @org.junit.jupiter.api.Test
    void findAllByAccount_GivenAccountId_ShouldReturnListOfTestResult() {
        when(testResultRepository.findByAccount_Id(anyInt())).thenReturn(expectedTestResults);

        int accountId = expectedTestResult.getAccount().getId();
        List<TestResult> actual = testResultService.findAllByAccount(accountId);

        assertThat(actual).isEqualTo(expectedTestResults);
        verify(testResultRepository, times(1)).findByAccount_Id(accountId);
    }

    @org.junit.jupiter.api.Test
    void save_RunningTestNotOverdueByTime_ShouldReturnTestResult() {
        LocalDateTime currentTime = LocalDateTime.now();

        runningTest.setStartTime(Timestamp.valueOf(currentTime
                .minusMinutes(1)));
        expectedTestResult.setCompletionDate(Timestamp.valueOf(currentTime));

        callingSaveMethodAssertTestResultTime(currentTime);

    }
    @org.junit.jupiter.api.Test
    void save_RunningTestOverdueByTime_ShouldReturnTestResult() {
        LocalDateTime currentTime = LocalDateTime.now();

        runningTest.setStartTime(Timestamp.valueOf(currentTime
                .minusMinutes(test.getDuration()+1)));
        LocalDateTime expectedFinishTime = runningTest.getStartTime().toLocalDateTime().plusMinutes(test.getDuration());
        expectedTestResult.setCompletionDate(Timestamp.valueOf(expectedFinishTime));

        callingSaveMethodAssertTestResultTime(currentTime);
    }

    @org.junit.jupiter.api.Test
    void save_WithAllCorrectAnswers_ShouldReturnTestResultWithMaxScore() {
        LocalDateTime currentTime = LocalDateTime.now();
        expectedTestResult.setCompletionDate(Timestamp.valueOf(currentTime));
        expectedTestResult.setScore(100);
        runningTest.setUserAnswers(Set.of(correctAnswer));

        callingSaveMethodAssertScore(currentTime);
    }

    @org.junit.jupiter.api.Test
    void save_WithBadAnswers_ShouldReturnTestResultWithZeroScore() {
        LocalDateTime currentTime = LocalDateTime.now();
        expectedTestResult.setCompletionDate(Timestamp.valueOf(currentTime));
        expectedTestResult.setScore(0);
        runningTest.setUserAnswers(Set.of(badAnswer));

        callingSaveMethodAssertScore(currentTime);
    }

    @org.junit.jupiter.api.Test
    void save_TestHasNotAnyQuestions_ShouldReturnTestResultWithMaxScore() {
        LocalDateTime currentTime = LocalDateTime.now();
        expectedTestResult.setCompletionDate(Timestamp.valueOf(currentTime));
        expectedTestResult.setScore(100);
        test.setQuestions(null);

        callingSaveMethodAssertScore(currentTime);
    }

    @org.junit.jupiter.api.Test
    void save_QuestionHasNotAnyAnswers_ShouldReturnTestResultWithZeroScore() {
        LocalDateTime currentTime = LocalDateTime.now();
        expectedTestResult.setCompletionDate(Timestamp.valueOf(currentTime));
        expectedTestResult.setScore(100);
        question.setAnswers(null);

        callingSaveMethodAssertScore(currentTime);
    }

    @org.junit.jupiter.api.Test
    void save_QuestionHasNotAnyCorrectAnswers_ShouldReturnTestResultWithZeroScore() {
        LocalDateTime currentTime = LocalDateTime.now();
        expectedTestResult.setCompletionDate(Timestamp.valueOf(currentTime));
        expectedTestResult.setScore(100);
        question.setAnswers(Set.of(badAnswer));

        callingSaveMethodAssertScore(currentTime);
    }

    private void callingSaveMethodAssertTestResultTime(LocalDateTime currentTime) {
        when(testResultRepository.save(any())).thenReturn(expectedTestResult);

        try(MockedStatic<LocalDateTime> localTimeMock = mockStatic(LocalDateTime.class)){
            localTimeMock.when(LocalDateTime::now).thenReturn(currentTime);
            localTimeMock.when(() -> LocalDateTime.of(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt())).thenCallRealMethod();

            TestResult actual = testResultService.save(runningTest);

            assertThat(runningTest.getTestResult()).isEqualTo(expectedTestResult);
            assertThat(actual).isEqualTo(expectedTestResult);
            assertThat(actual.getId()).isEqualTo(expectedTestResult.getId());
            verify(testResultRepository, times(1)).save(eq(expectedTestResult));
        }
    }

    private void callingSaveMethodAssertScore(LocalDateTime currentTime) {
        when(testResultRepository.save(any())).thenReturn(expectedTestResult);

        try(MockedStatic<LocalDateTime> localTimeMock = mockStatic(LocalDateTime.class)) {
            localTimeMock.when(LocalDateTime::now).thenReturn(currentTime);
            localTimeMock.when(() -> LocalDateTime.of(anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt())).thenCallRealMethod();

            TestResult actual = testResultService.save(runningTest);

            assertThat(actual.getScore()).isEqualTo(expectedTestResult.getScore());
            verify(testResultRepository, times(1)).save(eq(expectedTestResult));
        }
    }
}