package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.mapper.TestMapper;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.repository.SubjectRepository;
import com.epam.spring.testingapp.repository.TestRepository;
import com.epam.spring.testingapp.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public List<Test> findAll(String search, Integer subjectId, Pageable pageable) {
        search  = Objects.isNull(search)? "" : search;
        List<Test> tests = testRepository.findAllBySubjectId(search, subjectId, pageable);
        log.info("Founded tests = {}", tests);
        return tests;
    }

    @Override
    public Test find(int testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));

        log.info("Founded test = {}", test);
        return test;
    }

    @Override
    @Transactional
    public Test update(Test test, int testId) {
        testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));
        test.setId(testId);

        test = testRepository.save(test);
        log.info("Updated test#{} to = {}", testId, test);
        return test;
    }

    @Override
    @Transactional
    public Test create(Test test, int subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject with id %s not exist".formatted(subjectId)));

        test.setSubject(subject);
        test = testRepository.save(test);

        log.info("Created {}", test);
        return test;
    }

    @Override
    @Transactional
    public void delete(int testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));

        testRepository.delete(test);
        log.info("Deleted test#{}", testId);
    }
}
