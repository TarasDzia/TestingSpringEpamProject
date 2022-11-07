package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> findAll();

    SubjectDto create(SubjectDto subjectDto);

    void delete(int subjectId);

    SubjectDto update(SubjectDto subjectDto, int subjectId);

    SubjectDto find(int subjectId);

}
