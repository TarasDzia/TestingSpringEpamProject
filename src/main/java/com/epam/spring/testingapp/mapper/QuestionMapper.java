package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.model.Answer;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.Test;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(uses = {AnswerMapper.class})
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @IterableMapping(qualifiedByName = "mapQuestionDto")
    List<QuestionDto> mapQuestionsDtos (List<Question> questions);
    @IterableMapping(qualifiedByName = "mapQuestion")
    List<Question> mapQuestions (List<QuestionDto> questionDtos);

    @Named("mapQuestionDto")
    @Mapping(target = "testId", source = "test.id")
    QuestionDto mapDto(Question question);

    @Named("mapQuestion")
    @Mapping(target = "test", source = "testId")
    Question mapEntity(QuestionDto questionDto);

    @Mapping(target = "answers", source = "answers", qualifiedByName="mapAnswersWithoutCorrect")
    @Mapping(target = "testId", source = "test.id")
    QuestionDto mapDtoWithoutCorrect(Question question);

    @Named("mapAnswersWithoutCorrect")
    @IterableMapping(qualifiedByName="mapAnswerWithoutCorrect")
    Set<AnswerDto> mapAnswersDto(Set<Answer> answers);

    @Named("mapAnswerWithoutCorrect")
    @Mapping(target = "correct", ignore = true)
    @Mapping(target = "questionId", source = "question.id")
    AnswerDto mapAnswerDto(Answer answer);

    default Test mapTest(int id){
        return Test.builder().id(id).build();
    }
}
