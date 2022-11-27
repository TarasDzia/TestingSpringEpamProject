package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.model.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {QuestionMapper.class})
public interface TestMapper {
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    List<TestDto> toTestsDtos(List<Test> tests);
    List<Test> toTests(List<TestDto> testDtos);

    @Mapping(target = "subjectId", source = "subject.id")
    @Named("testToTestDto")
    TestDto toTestDto(Test test);

    @Named("toDtoWithoutQuestions")
    @Mapping(target = "subjectId", source = "subject.id")
    @Mapping(target = "questions", ignore = true)
    TestDto toDtoWithoutQuestions(Test test);

    @Mapping(target = "subject", source = "subjectId")
    @Mapping(target = "difficult", defaultValue = "MEDIUM")
    Test toTest(TestDto testDto);

    default Subject mapSubject(int subjectId) {
        return Subject.builder().id(subjectId).build();
    }
}
