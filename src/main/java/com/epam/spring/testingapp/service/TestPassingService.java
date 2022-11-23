package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.UserAnswerDto;

public interface TestPassingService {
    void startTest(Integer testId, Integer accountId);

    UserAnswerDto addUserAnswer(UserAnswerDto userAnswerDto, int accountId);

//    RunningTest getCurrentTestPassing(int accountId);

    QuestionDto getQuestion(Integer sequenceNumber, Integer accountId);
}
