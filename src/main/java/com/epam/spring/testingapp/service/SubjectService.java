package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> findAll();

    SubjectDto find(int subjectId);

    SubjectDto create(SubjectDto subjectDto);

    SubjectDto update(SubjectDto subjectDto, int subjectId);

    void delete(int subjectId);

}
