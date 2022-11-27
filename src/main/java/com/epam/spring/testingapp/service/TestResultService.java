package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.model.RunningTest;
import com.epam.spring.testingapp.model.TestResult;

import java.util.List;

public interface TestResultService {
    List<TestResultDto> findAllByAccount(int accountId);
    TestResult saveTestResult(int score, RunningTest runningTest);
}
