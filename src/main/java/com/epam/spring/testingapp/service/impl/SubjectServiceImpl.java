package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.UnprocessableEntityException;
import com.epam.spring.testingapp.mapper.SubjectMapper;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.repository.SubjectRepository;
import com.epam.spring.testingapp.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    @Override
    public List<SubjectDto> findAll() {
        List<Subject> subjects = subjectRepository.findAll();
        log.info("Founded subjects = {}", subjects);
        return SubjectMapper.INSTANCE.toSubjectsDtos(subjects);
    }

    @Override
    public SubjectDto create(SubjectDto subjectDto) {
        Subject subject = SubjectMapper.INSTANCE.toSubject(subjectDto);

        try {
            subject = subjectRepository.save(subject);
        }catch(DataIntegrityViolationException e ){
            throw new UnprocessableEntityException("Subject with that name already exists", e);
        }

        log.info("Created subject = {}", subject);
        return SubjectMapper.INSTANCE.toSubjectDto(subject);
    }

    @Override
    @Transactional
    public SubjectDto update(SubjectDto subjectDto, int subjectId) {
        subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject with id %s not exist".formatted(subjectId)));

        Subject subject = SubjectMapper.INSTANCE.toSubject(subjectDto);
        subject.setId(subjectId);

        try {
            subject = subjectRepository.save(subject);
        }catch(DataIntegrityViolationException e ){
            throw new UnprocessableEntityException("Subject with that name already exists", e);
        }

        log.info("Updated subject#{} to = {}", subjectId, subject);
        return SubjectMapper.INSTANCE.toSubjectDto(subject);
    }

    @Override
    public SubjectDto find(int subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject with id %s not exist".formatted(subjectId)));

        log.info("Founded subject = {}", subject);
        return SubjectMapper.INSTANCE.toSubjectDto(subject);
    }

    @Override
    @Transactional
    public void delete(int subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject with id %s not exist".formatted(subjectId)));

        subjectRepository.delete(subject);
        log.info("Deleted subject#{}", subjectId);
    }
}
