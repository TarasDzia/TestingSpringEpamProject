package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.QuestionDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> findAll(int testId);

    QuestionDto find(int questionId);

    QuestionDto create(QuestionDto questionDto);

    QuestionDto update(QuestionDto questionDto, int questionId);

    void delete(int questionId);
}
