package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.TestDto;

import java.util.List;

public interface TestService {
    List<TestDto> findAll(String search, String sorting, Integer subject);

    TestDto update(TestDto testDto, int testId);

    TestDto create(TestDto testDto, int subjectId);

    TestDto find(int testId);

    void delete(int testId);
}
