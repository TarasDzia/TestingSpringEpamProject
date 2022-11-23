package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.AnswerDto;

import java.util.Set;

public interface AnswerService {
    Set<AnswerDto> findAll(int questionId);

    AnswerDto find(int answerId);

    AnswerDto createForQuestion(AnswerDto answerDto, int questionId);

    AnswerDto update(AnswerDto answerDto, int answerId);

    void delete(int answerId);
}
