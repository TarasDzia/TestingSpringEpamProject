package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.Test;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {
    List<Test> findAll(String search, Integer subjectId, Pageable pageable);

    Test update(Test test, int testId);

    Test create(Test test, int subjectId);

    Test find(int testId);

    void delete(int testId);
}
