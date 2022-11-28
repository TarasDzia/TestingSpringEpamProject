package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.RunningTest;
import com.epam.spring.testingapp.model.TestResult;

import java.util.List;

public interface TestResultService {
    List<TestResult> findAllByAccount(int accountId);
    TestResult saveTestResult(int score, RunningTest runningTest);
}
