package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.dto.TestDtoSubject;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.model.Test;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {QuestionMapper.class})
public interface TestMapper {
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    @IterableMapping(qualifiedByName = "toTestDtoSub")
    List<TestDtoSubject> toTestsDtos(List<Test> tests);
    List<Test> toTests(List<TestDtoSubject> testDtoSubjects);

    @Named("toTestDtoSub")
    @Mapping(target = "questionsQuantity", source = "questions", qualifiedByName = "mapQuestionsToSize")
    TestDtoSubject toTestDtoSub(Test test);

    @Named("toTestDto")
    @Mapping(target = "subjectId", source = "subject.id")
    TestDto toTestDto(Test test);

    @Named("toDtoWithoutQuestions")
    @Mapping(target = "questionsQuantity", source = "questions", qualifiedByName = "mapQuestionsToSize")
    TestDtoSubject toDtoWithoutQuestions(Test test);

    @Mapping(target = "difficult", defaultValue = "MEDIUM")
    Test toTest(TestDtoSubject testDtoSubject);

    @Mapping(target = "difficult", defaultValue = "MEDIUM")
    @Mapping(target = "subject", source = "subjectId")
    Test toTest(TestDto testDtoSubject);


    @Named("mapQuestionsToSize")
    default int mapQuestionsToSize(List<Question> questions){
        return questions.size();
    }
    default Subject mapSubject(int subjectId) {
        if(subjectId == 0)
            return null;
        return Subject.builder().id(subjectId).build();
    }
}
