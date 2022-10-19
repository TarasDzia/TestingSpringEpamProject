package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Override
    public List<QuestionDto> findAll(int testId) {
        return null;
    }

    @Override
    public QuestionDto find(int questionId) {
        return null;
    }

    @Override
    public QuestionDto create(QuestionDto questionDto) {
        return null;
    }

    @Override
    public QuestionDto update(QuestionDto questionDto, int questionId) {
        return null;
    }

    @Override
    public void delete(int questionId) {

    }
}
