package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.RunningTest;
import com.epam.spring.testingapp.model.TestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface RunningTestService {
    RunningTest start(int testId, int accountId);
    RunningTest addUserAnswer(Set<Integer> answers, int accountId);
    Page<Question> getQuestion(Pageable pageable, Integer accountId);
    TestResult finish(int accountId);

    RunningTest findCurrent(int accountId);
}
