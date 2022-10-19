package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Override
    public List<SubjectDto> findAll() {
        return null;
    }

    @Override
    public SubjectDto create(SubjectDto subjectDto) {
        return null;
    }

    @Override
    public void delete(int subjectId) {

    }

    @Override
    public SubjectDto update(SubjectDto subjectDto, int subjectId) {
        return null;
    }
}
