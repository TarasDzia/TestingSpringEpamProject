package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubjectService {
    List<Subject> findAll();

    Page<Subject> findAll(String search, Pageable pageable);

    Subject create(Subject subject);

    Subject update(Subject subject, int subjectId);

    void delete(int subjectId);

}
