package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.RunningTestDto;
import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.model.RunningTest;

import javax.transaction.Transactional;
import java.util.Set;

public interface RunningTestService {
    RunningTestDto startTest(int testId, int accountId);
    RunningTestDto addUserAnswer(Set<Integer> answers, int accountId);
    QuestionDto getQuestion(Integer sequenceNumber, Integer accountId);
    TestResultDto finishTestById(int accountId);
}
