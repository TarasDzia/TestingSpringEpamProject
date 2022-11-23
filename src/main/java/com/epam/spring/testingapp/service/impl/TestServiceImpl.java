package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.TestDto;
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

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public List<TestDto> findAll(String search, Integer subjectId, Pageable pageable) {
        search  = Objects.isNull(search)? "" : search;
        List<Test> tests = testRepository.findAllBySubjectId(search, subjectId, pageable);
        log.info("Founded tests = {}", tests);
        return TestMapper.INSTANCE.testsToTestsDtos(tests);
    }

    @Override
    public TestDto find(int testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));

        log.info("Founded test = {}", test);
        return TestMapper.INSTANCE.testToTestDto(test);
    }

    @Override
    public TestDto update(TestDto testDto, int testId) {
        testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));

        Test test = TestMapper.INSTANCE.testDtoToTest(testDto);
        test.setId(testId);

        test = testRepository.save(test);
        log.info("Updated test#{} to = {}", testId, test);
        return TestMapper.INSTANCE.testToTestDto(test);
    }

    @Override
    public TestDto create(TestDto testDto, int subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject with id %s not exist".formatted(subjectId)));

        Test test = TestMapper.INSTANCE.testDtoToTest(testDto);

        test.setSubject(subject);
        test = testRepository.save(test);

        log.info("Created {}", test);
        return TestMapper.INSTANCE.testToTestDto(test);
    }

    @Override
    public void delete(int testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));

        testRepository.delete(test);
        log.info("Deleted test#{}", testId);
    }
}
