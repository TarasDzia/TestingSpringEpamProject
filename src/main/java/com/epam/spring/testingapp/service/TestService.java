package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.TestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {
    List<TestDto> findAll(String search, Integer subjectId, Pageable pageable);

    TestDto update(TestDto testDto, int testId);

    TestDto create(TestDto testDto, int subjectId);

    TestDto find(int testId);

    void delete(int testId);
}
