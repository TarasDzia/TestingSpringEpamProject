package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.dto.UserAnswerDto;

import java.util.List;
import java.util.Set;

public interface TestResultService {
    List<TestResultDto> findAllByAccount(int accountId);
    TestResultDto passTest(Set<UserAnswerDto> userAnswers, int testId, int accountId);
}
