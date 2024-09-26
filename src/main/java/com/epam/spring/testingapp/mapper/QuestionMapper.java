package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    List<QuestionDto> questionsToQuestionsDtos(List<Question> questions);

    List<Question> questionsDtosToQuestions(List<QuestionDto> questionDtos);

    @Mapping(target = "testId", source = "question.test.id")
    QuestionDto questionToQuestionDto(Question question);

    @Mapping(target = "test", source = "testId")
    Question questionDtoToQuestion(QuestionDto questionDto);

    default Test mapTest(int testId) {
        return Test.builder().id(testId).build();
    }
}
