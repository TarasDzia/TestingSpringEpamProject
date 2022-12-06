package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findAll(int testId);

    Question find(int questionId);

    Question createForTest(Question questionDto, int testId);

    Question update(Question question, int questionId);

    void delete(int questionId);
}
