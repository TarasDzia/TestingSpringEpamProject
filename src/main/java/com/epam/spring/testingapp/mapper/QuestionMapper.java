package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.Test;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AnswerMapper.class})
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @IterableMapping(qualifiedByName = "mapQuestionDto")
    List<QuestionDto> mapQuestionsDtos (List<Question> questions);
    @IterableMapping(qualifiedByName = "mapQuestion")
    List<Question> mapQuestions (List<QuestionDto> questionDtos);

    @Named("mapQuestionDto")
    @Mapping(target = "testId", source = "test.id")
    QuestionDto mapQuestionDto(Question question);

    @Named("mapQuestion")
    @Mapping(target = "test", source = "testId")
    Question mapQuestion(QuestionDto questionDto);

    @Mapping(target = "answers", source = "answers", qualifiedByName="mapAnswersDtoWithoutCorrect")
    @Mapping(target = "testId", source = "test.id")
    QuestionDto mapDtoWithoutCorrect(Question question);

    default Test mapTest(int id){
        return Test.builder().id(id).build();
    }
}
