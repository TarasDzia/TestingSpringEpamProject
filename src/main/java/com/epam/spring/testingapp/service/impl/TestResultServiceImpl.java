package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.mapper.TestResultMapper;
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

@Slf4j
@RequiredArgsConstructor
@Service
public class TestResultServiceImpl implements TestResultService {
    private final TestResultRepository testResultRepository;

    @Override
    public List<TestResult> findAllByAccount(int accountId) {
        List<TestResult> testResults = testResultRepository.findByAccount_Id(accountId);

        log.info("Founded testResults of account#{} = {}", accountId, testResults);
        return testResults;
    }

    @Override
    @Transactional
    public TestResult saveTestResult(int score, RunningTest runningTest) {
        Timestamp latestCompletionTime = Timestamp.valueOf(runningTest.getStartTime().toLocalDateTime().plusMinutes(runningTest.getTest().getDuration()));
        Timestamp completionDate = Timestamp.valueOf(LocalDateTime.now());

        if(completionDate.after(latestCompletionTime))
            completionDate = latestCompletionTime;

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
}
