package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.service.TestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public List<TestDto> findAll(String search, String sorting, String subject) {
        return null;
    }

    @Override
    public TestDto update(TestDto testDto, int testId) {
        return null;
    }

    @Override
    public TestDto create(TestDto testDto) {
        return null;
    }

    @Override
    public TestDto find(int testId) {
        return null;
    }

    @Override
    public void delete(int testId) {

    }
}
