package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.dto.UserAnswerDto;
import com.epam.spring.testingapp.service.TestResultService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestResultServiceImpl implements TestResultService {
    @Override
    public List<TestResultDto> findAllByAccount(int accountId) {
        return null;
    }

    @Override
    public TestResultDto passTest(List<UserAnswerDto> userAnswers, int testId) {
        return null;
    }
}
