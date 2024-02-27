package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.RunningTest;
import com.epam.spring.testingapp.model.TestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestResultService {
    Page<TestResult> findAllByAccount(int accountId, Pageable pageable);

    TestResult save(RunningTest runningTest);
}
