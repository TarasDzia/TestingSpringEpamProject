package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.mapper.SubjectMapper;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {
    @Override
    public List<SubjectDto> findAll() {
        List<Subject> subjects = List.of(Subject.builder().id(1).name("Math").build(),
                Subject.builder().id(2).name("Not math").build());
        log.info("Founded subjects = {}", subjects);
        return SubjectMapper.INSTANCE.subjectsToSubjectsDtos(subjects);
    }

    @Override
    public SubjectDto create(SubjectDto subjectDto) {
        Subject subject = SubjectMapper.INSTANCE.subjectDtoToSubject(subjectDto);
        subject.setId(1);
        log.info("Created subject = {}", subject);
        return SubjectMapper.INSTANCE.subjectToSubjectDto(subject);
    }

    @Override
    public SubjectDto update(SubjectDto subjectDto, int subjectId) {
        Subject subject = SubjectMapper.INSTANCE.subjectDtoToSubject(subjectDto);

//        saving
        subject.setId(subjectId);

        log.info("Updated subject#{} to = {}", subjectId, subject);
        return SubjectMapper.INSTANCE.subjectToSubjectDto(subject);
    }

    @Override
    public SubjectDto find(int subjectId) {
        Subject subject = Subject.builder().id(subjectId).name("Math").build();

        log.info("Founded subject = {}", subject);
        return SubjectMapper.INSTANCE.subjectToSubjectDto(subject);
    }

    @Override
    public void delete(int subjectId) {
        log.info("Deleted subject#{}", subjectId);
    }
}
