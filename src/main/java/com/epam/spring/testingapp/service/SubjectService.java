package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findAll();

    Subject create(Subject subject);

    Subject update(Subject subject, int subjectId);

    void delete(int subjectId);

}
