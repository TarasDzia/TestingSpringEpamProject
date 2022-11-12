package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.model.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TestMapper {
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    List<TestDto> testsToTestsDtos(List<Test> tests);

    List<Test> testsDtosToTests(List<TestDto> testDtos);

    @Mapping(target = "subjectId", source = "test.subject.id")
    TestDto testToTestDto(Test test);

    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "difficult", defaultValue = "MEDIUM")
    Test testDtoToTest(TestDto testDto);

    default Subject mapSubject(int subjectId) {
        return Subject.builder().id(subjectId).build();
    }
}
