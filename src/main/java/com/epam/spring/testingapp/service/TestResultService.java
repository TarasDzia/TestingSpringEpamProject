package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.dto.UserAnswerDto;

import java.util.List;

public interface TestResultService {
    List<TestResultDto> findAllByAccount(int accountId);

    TestResultDto passTest(List<UserAnswerDto> userAnswers, int testId);
}
