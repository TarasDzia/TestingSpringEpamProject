package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.model.*;
import com.epam.spring.testingapp.repository.TestResultRepository;
import com.epam.spring.testingapp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestResultServiceImpl implements TestResultService {
    private static final int MAX_SCORE = 100;
    private final TestResultRepository testResultRepository;

    @Override
    public List<TestResult> findAllByAccount(int accountId) {
        List<TestResult> testResults = testResultRepository.findByAccount_Id(accountId);

        log.info("Founded testResults of account#{} = {}", accountId, testResults);
        return testResults;
    }

    @Override
    @Transactional
    public TestResult save(RunningTest runningTest) {
        Timestamp latestCompletionTime = Timestamp.valueOf(runningTest.getStartTime().toLocalDateTime().plusMinutes(runningTest.getTest().getDuration()));
        Timestamp completionDate = Timestamp.valueOf(LocalDateTime.now());

        if(completionDate.after(latestCompletionTime))
            completionDate = latestCompletionTime;

        int score = calculateScoreForRunningTest(runningTest);
        TestResult testResult = TestResult.builder()
                .account(runningTest.getAccount())
                .runningTest(runningTest)
                .score(score)
                .completionDate(completionDate)
                .build();

        testResult = testResultRepository.save(testResult);
        runningTest.setTestResult(testResult);

        return  testResult;
    }
    private static int calculateScoreForRunningTest(RunningTest runningTest) {
        log.info("Start scoring of RunningTest#{} with userAnswers {}", runningTest.getId(), runningTest.getUserAnswers());
        List<Question> questions = runningTest.getTest().getQuestions();

        if (questions == null){
            log.debug("Test hasnt any questions. Returning max score.");
            return MAX_SCORE;
        }


        int countOfQuestions = questions.size();
        int countOfCorrectAnswers = 0;

        for (Question question: questions) {
            log.debug("Answers for question#{} are evaluating...", question.getId());

//          If the answers for these question exists
            if(question.getAnswers() == null){
                countOfCorrectAnswers++;
                log.debug("Question doesnt contain any ANSWERS skipping... This question wont effect the result of scoring.");
                continue;
            }

//          Founding expected correct answers of this question
            Set<Answer> correctAnswers = question.getAnswers().stream().filter(Answer::isCorrect).collect(Collectors.toSet());
            log.info("Founding expected correct answers of question#{}#{} = {}", question.getId(), question.getDescription(), correctAnswers);
            if (correctAnswers.isEmpty()){
                countOfCorrectAnswers++;
                log.debug("Question doesnt contain any CORRECT ANSWERS skipping... This question wont effect the result of scoring.");
                continue;
            }

//          Founding user answer for this question
            Set<Answer> userChosenAnswersForCurrentQuestion = runningTest.getUserAnswers()
                    .stream().filter(userChosenAnswers -> userChosenAnswers.getQuestion().getId() == question.getId())
                    .collect(Collectors.toSet());

            log.debug("Founding user answer for question#{}#{} = {}", question.getId(), question.getDescription(), userChosenAnswersForCurrentQuestion);

            if (correctAnswers.equals(userChosenAnswersForCurrentQuestion)){
                countOfCorrectAnswers++;
                log.debug("Answer for question#{}#{} is correct", question.getId(), question.getDescription());
            }else{
                log.debug("Answer for question#{}#{} is bad!", question.getId(), question.getDescription());
            }

            log.debug("Current number of correct answers = {}, needed value to get max score = {}", countOfCorrectAnswers, countOfQuestions);
        }

        int score = (int) Math.round((double) countOfCorrectAnswers / ((double) countOfQuestions) * MAX_SCORE);
        log.info("Ended scoring of RunningTest#{} with value={}", runningTest.getId(), score);
        return score;
    }
}
