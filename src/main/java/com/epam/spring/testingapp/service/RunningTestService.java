package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.RunningTest;
import com.epam.spring.testingapp.model.TestResult;

import java.util.Set;

public interface RunningTestService {
    RunningTest startTest(int testId, int accountId);
    RunningTest addUserAnswer(Set<Integer> answers, int accountId);
    Question getQuestion(Integer sequenceNumber, Integer accountId);
    TestResult finishTestById(int accountId);
}
