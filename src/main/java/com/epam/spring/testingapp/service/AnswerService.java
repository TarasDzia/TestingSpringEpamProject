package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.AnswerDto;

import java.util.List;

public interface AnswerService {
    List<AnswerDto> findAll(int questionId);

    AnswerDto find(int answerId);

    AnswerDto create(AnswerDto answerDto, int questionId);

    AnswerDto update(AnswerDto answerDto, int answerId);

    void delete(int answerId);
}
