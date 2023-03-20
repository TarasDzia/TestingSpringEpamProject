package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.UnprocessableEntityException;
import com.epam.spring.testingapp.mapper.SubjectMapper;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.repository.SubjectRepository;
import com.epam.spring.testingapp.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    @Override
    public List<Subject> findAll() {
        List<Subject> subjects = subjectRepository.findAll();
        log.info("Founded subjects = {}", subjects);
        return subjects;
    }

    @Override
    public Page<Subject> findAll(String search, Pageable pageable) {
        search  = Objects.isNull(search)? "" : search;
        Page<Subject> subjects = subjectRepository.findAll(search, pageable);
        log.info("Founded subjects = {}", subjects);
        return subjects;
    }

    @Override
    public Subject create(Subject subject) {
        try {
            subject = subjectRepository.save(subject);
        }catch(DataIntegrityViolationException e ){
            throw new UnprocessableEntityException("Subject with that name already exists", e);
        }

        log.info("Created subject = {}", subject);
        return subject;
    }

    @Override
    @Transactional
    public Subject update(Subject subject, int subjectId) {
        subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject with id %s not exist".formatted(subjectId)));

        subject.setId(subjectId);

        try {
            subject = subjectRepository.save(subject);
        }catch(DataIntegrityViolationException e ){
            throw new UnprocessableEntityException("Subject with that name already exists", e);
        }

        log.info("Updated subject#{} to = {}", subjectId, subject);
        return subject;
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
