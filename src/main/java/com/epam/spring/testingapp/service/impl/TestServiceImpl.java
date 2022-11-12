package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.mapper.TestMapper;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TestServiceImpl implements TestService {
    @Override
    public List<TestDto> findAll(String search, String sorting, Integer subjectId) {
        List<Test> tests = List.of(Test.builder().id(1).build(), Test.builder().id(2).build());
        log.info("Founded tests = {}", tests);
        return TestMapper.INSTANCE.testsToTestsDtos(tests);
    }

    @Override
    public TestDto find(int testId) {
        Test test = Test.builder().id(1).build();
        log.info("Founded test = {}", test);
        return TestMapper.INSTANCE.testToTestDto(test);
    }

    @Override
    public TestDto update(TestDto testDto, int testId) {
        Test test = TestMapper.INSTANCE.testDtoToTest(testDto);
        test.setId(testId);
        log.info("Updated test#{} to = {}", testId, test);
        return TestMapper.INSTANCE.testToTestDto(test);
    }

    @Override
    public TestDto create(TestDto testDto, int subjectId) {
        Test test = TestMapper.INSTANCE.testDtoToTest(testDto);
//        saving
        test.setId(1);
        test.setSubject(Subject.builder().id(subjectId).build());

        log.info("Created test = {}", test);
        return TestMapper.INSTANCE.testToTestDto(test);
    }

    @Override
    public void delete(int testId) {
        log.info("Deleted test#{}", testId);
    }
}
