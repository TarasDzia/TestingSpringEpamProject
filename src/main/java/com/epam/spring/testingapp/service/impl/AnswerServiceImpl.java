package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.service.AnswerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Override
    public List<AnswerDto> findAll(int questionId) {
        return null;
    }

    @Override
    public AnswerDto find(int answerId) {
        return null;
    }

    @Override
    public AnswerDto create(AnswerDto answerDto, int questionId) {
        return null;
    }

    @Override
    public AnswerDto update(AnswerDto answerDto, int answerId) {
        return null;
    }

    @Override
    public void delete(int answerId) {

    }
}
