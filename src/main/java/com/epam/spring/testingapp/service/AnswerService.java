package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.Answer;

import java.util.Set;

public interface AnswerService {
    Set<Answer> findAll(int questionId);

    Answer find(int answerId);

    Answer createForQuestion(Answer answer, int questionId);

    Answer update(Answer answer, int answerId);

    void delete(int answerId);
}
