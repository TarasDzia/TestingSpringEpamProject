package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.*;
import com.epam.spring.testingapp.model.*;
import com.epam.spring.testingapp.model.enumerate.TestDifficult;
import com.epam.spring.testingapp.repository.AccountRepository;
import com.epam.spring.testingapp.repository.AnswerRepository;
import com.epam.spring.testingapp.repository.RunningTestRepository;
import com.epam.spring.testingapp.repository.TestRepository;
import com.epam.spring.testingapp.service.TestResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.spring.testingapp.utils.*;
import static org.assertj.core.api.AssertionsForClassTypes.anyOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RunningTestServiceImplTest {
    @Mock
    private TestRepository testRepositoryMock;
    @Mock
    private AccountRepository accountRepositoryMock;
    @Mock
    private RunningTestRepository runningTestRepositoryMock;
    @Mock
    private AnswerRepository answerRepositoryMock;
    @Mock
    private TestResultService testResultServiceMock;

    @InjectMocks
    private RunningTestServiceImpl runningTestService;

    private com.epam.spring.testingapp.model.Test test;

    private RunningTest expectedRunningTest;
    private RunningTest expectedEmptyRunningTest;
    private Account account;
    private Answer answer2;
    private Answer answer1;
    private Set<Answer> answerSet;
    private Question question;
    private TestResult expectedTestResult;

    @BeforeEach
    void setUp() {
        account = getAccount();
        test = getTest();
        question = getQuestion(test);
        answer1 = getCorrectAnswer(question);
        answer2 = getBadAnswer(question);
        expectedEmptyRunningTest = getRunningTest(account,test);
        expectedRunningTest = getRunningTest(account,test);
        expectedTestResult = getTestResult(account);

        answerSet = Set.of(answer1, answer2);

        test.setQuestions(List.of(question));
        question.setAnswers(answerSet);
        expectedRunningTest.setUserAnswers(answerSet);
    }

    @Test
    void startTest_GivenTestIdAndAccountId_ShouldReturnRunningTest() {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        expectedEmptyRunningTest.setStartTime(currentTime);
        int accountId = expectedEmptyRunningTest.getAccount().getId();
        int testId = expectedEmptyRunningTest.getTest().getId();

        when(testRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(test));
        when(accountRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(account));
        when(runningTestRepositoryMock.save(any())).thenReturn(expectedEmptyRunningTest);

        try(MockedStatic<Timestamp> timestampMock = mockStatic(Timestamp.class)){
            timestampMock.when(() -> Timestamp.valueOf(any(LocalDateTime.class))).thenReturn(currentTime);

            RunningTest actual = runningTestService.start(testId, accountId);

            assertThat(actual).isEqualTo(expectedEmptyRunningTest);
            assertThat(actual.getId()).isEqualTo(expectedEmptyRunningTest.getId());
            verify(runningTestRepositoryMock, times(1)).findAllNotFinished(accountId);
            verify(accountRepositoryMock, times(1)).findById(accountId);
            verify(testRepositoryMock, times(1)).findById(testId);
            verify(runningTestRepositoryMock, times(1)).save(eq(expectedEmptyRunningTest));
        }
    }

    @Test
    void startTest_ShouldFinishAllPreviouslyStartedTests() {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        expectedEmptyRunningTest.setStartTime(currentTime);
        int accountId = expectedEmptyRunningTest.getAccount().getId();
        int testId = expectedEmptyRunningTest.getTest().getId();
        List<RunningTest> testsThatEnding = List.of(expectedRunningTest);

        when(accountRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(account));
        when(testRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(test));
        when(runningTestRepositoryMock.findAllNotFinished(any())).thenReturn(testsThatEnding);
        when(testResultServiceMock.save(any())).thenReturn(expectedTestResult);

        runningTestService.start(testId, accountId);

        verify(accountRepositoryMock, times(1)).findById(accountId);
        verify(runningTestRepositoryMock, times(1)).findAllNotFinished(accountId);
        verify(testResultServiceMock, times(testsThatEnding.size())).save(expectedRunningTest);
        verify(runningTestRepositoryMock, times(testsThatEnding.size())).save(expectedRunningTest);
    }

    @Test
    void startTest_GivenTestIdAndAccountId_account_not_exist_ShouldThrowException() {
        int accountId = expectedRunningTest.getAccount().getId();
        int testId = expectedRunningTest.getTest().getId();

        when(accountRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            runningTestService.start(testId, accountId);
        });

        verify(accountRepositoryMock, times(1)).findById(accountId);
    }

    @Test
    void startTest_GivenTestIdAndAccountId_test_not_exist_ShouldThrowException() {
        int accountId = expectedRunningTest.getAccount().getId();
        int testId = expectedRunningTest.getTest().getId();

        when(testRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
        when(accountRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(account));

        assertThrows(NotFoundException.class, () -> {
            runningTestService.start(testId, accountId);
        });
        verify(testRepositoryMock, times(1)).findById(testId);
    }

    @Test
    void addUserAnswer_ShouldReturnRunningTest_WithTwoAnswers() {
        Set<Integer> answersIds = Set.of(2, 4);
        int accountId = account.getId();

        when(runningTestRepositoryMock.findFirstByAccount_IdAndTestResultNull(anyInt())).thenReturn(Optional.ofNullable(expectedEmptyRunningTest));
        when(answerRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(answer1)).thenReturn(Optional.ofNullable(answer2));

        RunningTest actual = runningTestService.addUserAnswer(answersIds, accountId);

        assertThat(actual).isEqualTo(expectedRunningTest);
        assertThat(actual.getUserAnswers()).isEqualTo(answerSet);
        verify(runningTestRepositoryMock, times(1)).findFirstByAccount_IdAndTestResultNull(accountId);
        verify(answerRepositoryMock, times(2)).findById(anyInt());
        verify(runningTestRepositoryMock, times(1)).save(eq(expectedRunningTest));
    }

    @Test
    void addUserAnswer_ShouldThrowNoTestRunningEx_NoRunningTests() {
        Set<Integer> answersIds = Set.of(2, 4);
        int accountId = account.getId();

        when(runningTestRepositoryMock.findFirstByAccount_IdAndTestResultNull(anyInt())).thenReturn(Optional.empty());

        assertThrows(NoTestRunningForAccountException.class, () -> {
            runningTestService.addUserAnswer(answersIds, accountId);
        });

        verify(runningTestRepositoryMock, times(1)).findFirstByAccount_IdAndTestResultNull(accountId);
    }

    @Test
    void addUserAnswer_ShouldThrowUnprocessableEx_AnswersIdsNotRelevantToTest() {
        Set<Integer> answersIds = Set.of(2, 4);
        int accountId = account.getId();

        answer1.setQuestion(Question.builder().test(com.epam.spring.testingapp.model.Test.builder().build()).build());
        when(runningTestRepositoryMock.findFirstByAccount_IdAndTestResultNull(anyInt())).thenReturn(Optional.ofNullable(expectedEmptyRunningTest));
        when(answerRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(answer1)).thenReturn(Optional.ofNullable(answer2));


        assertThrows(UnprocessableEntityException.class, () -> {
            runningTestService.addUserAnswer(answersIds, accountId);
        });

        verify(runningTestRepositoryMock, times(1)).findFirstByAccount_IdAndTestResultNull(accountId);
        verify(answerRepositoryMock, times(1)).findById(anyInt());
    }

    @Test
    void addUserAnswer_ShouldThrowUnprocessableEx_AnswerNotExist() {
        Set<Integer> answersIds = Set.of(2, 4);
        int accountId = account.getId();

        when(runningTestRepositoryMock.findFirstByAccount_IdAndTestResultNull(anyInt())).thenReturn(Optional.ofNullable(expectedEmptyRunningTest));
        when(answerRepositoryMock.findById(any())).thenReturn(Optional.empty());


        assertThrows(UnprocessableEntityException.class, () -> {
            runningTestService.addUserAnswer(answersIds, accountId);
        });

        verify(runningTestRepositoryMock, times(1)).findFirstByAccount_IdAndTestResultNull(accountId);
        verify(answerRepositoryMock, times(1)).findById(anyInt());
    }

    @Test
    void addUserAnswer_ShouldThrowUnprocessableEx_RunningTestTimeIsUp() {
        Set<Integer> answersIds = Set.of(2, 4);
        int accountId = account.getId();

        expectedEmptyRunningTest.setStartTime(Timestamp.valueOf(LocalDateTime.now().minusMinutes(test.getDuration()+1)));

        when(runningTestRepositoryMock.findFirstByAccount_IdAndTestResultNull(anyInt())).thenReturn(Optional.ofNullable(expectedEmptyRunningTest));

        assertThrows(TestTimeIsUpException.class, () -> {
            runningTestService.addUserAnswer(answersIds, accountId);
        });

        verify(runningTestRepositoryMock, times(1)).findFirstByAccount_IdAndTestResultNull(accountId);
    }

    @Test
    void getQuestion_GivenSequenceNumberAndAccountId_ShouldReturnQuestion() {
        int accountId = account.getId();
        int sequenceNumber = 0;

        when(runningTestRepositoryMock.findFirstByAccount_IdAndTestResultNull(anyInt())).thenReturn(Optional.ofNullable(expectedEmptyRunningTest));

        Question actual = runningTestService.getQuestion(sequenceNumber, accountId);

        assertThat(actual).isEqualTo(question);
        assertThat(actual.getId()).isEqualTo(question.getId());
        verify(runningTestRepositoryMock, times(1)).findFirstByAccount_IdAndTestResultNull(accountId);
    }

    @Test
    void getQuestion_ShouldThrowNoTestRunningEx() {
        int accountId = account.getId();
        int sequenceNumber = 0;

        when(runningTestRepositoryMock.findFirstByAccount_IdAndTestResultNull(anyInt())).thenReturn(Optional.empty());

        assertThrows(NoTestRunningForAccountException.class, () -> {
            runningTestService.getQuestion(sequenceNumber, accountId);
        });

        verify(runningTestRepositoryMock, times(1)).findFirstByAccount_IdAndTestResultNull(accountId);
    }

    @Test
    void getQuestion_ShouldThrowOutOfBoundsEx() {
        int accountId = account.getId();
        int sequenceNumber = Integer.MAX_VALUE;

        when(runningTestRepositoryMock.findFirstByAccount_IdAndTestResultNull(anyInt())).thenReturn(Optional.ofNullable(expectedEmptyRunningTest));

        assertThrows(SequenceNumberOutOfBoundsException.class, () -> {
            runningTestService.getQuestion(sequenceNumber, accountId);
        });

        verify(runningTestRepositoryMock, times(1)).findFirstByAccount_IdAndTestResultNull(accountId);
    }

    @Test
    void finishTestById_ShouldReturnTestResult() {
        int accountId = account.getId();

        when(accountRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(account));
        when(runningTestRepositoryMock.findFirstByAccount_IdAndTestResultNull(anyInt())).thenReturn(Optional.ofNullable(expectedRunningTest));
        when(testResultServiceMock.save(any())).thenReturn(expectedTestResult);

        TestResult actual = runningTestService.finish(accountId);

        assertThat(actual.getId()).isEqualTo(expectedTestResult.getId());
        assertThat(actual).isEqualTo(expectedTestResult);
        verify(accountRepositoryMock, times(1)).findById(accountId);
        verify(runningTestRepositoryMock, times(1)).findFirstByAccount_IdAndTestResultNull(accountId);
        verify(testResultServiceMock, times(1)).save(expectedRunningTest);
    }

    @Test
    void finishTestById_ShouldThrowNotFoundEx() {
        int accountId = account.getId();

        when(accountRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            runningTestService.finish(accountId);
        });

        verify(accountRepositoryMock, times(1)).findById(accountId);
    }

    @Test
    void finishTestById_ShouldThrowNoTestRunningEx() {
        int accountId = account.getId();

        when(accountRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(account));
        when(runningTestRepositoryMock.findFirstByAccount_IdAndTestResultNull(anyInt())).thenReturn(Optional.empty());

        assertThrows(NoTestRunningForAccountException.class, () -> {
            runningTestService.finish(accountId);
        });

        verify(accountRepositoryMock, times(1)).findById(accountId);
        verify(runningTestRepositoryMock, times(1)).findFirstByAccount_IdAndTestResultNull(accountId);
    }
}