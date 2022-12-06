package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.model.Answer;
import com.epam.spring.testingapp.model.Question;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    Set<AnswerDto> toAnswerDtos(Set<Answer> answers);
    @Named("mapAnswersDtoWithoutCorrect")
    @IterableMapping(qualifiedByName="mapAnswerDtoWithoutCorrect")
    Set<AnswerDto> mapAnswersDtoWithoutCorrect(Set<Answer> answers);
    Set<Answer> toAnswers(Set<AnswerDto> answerDtos);

    @Mapping(target = "questionId", source = "question.id")
    AnswerDto toAnswerDto(Answer answer);
    @Named("mapAnswerDtoWithoutCorrect")
    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "correct", ignore = true)
    AnswerDto toAnswerDtoWithoutCorrect(Answer answer);
    @Mapping(target = "question", source = "questionId")
    @Mapping(target = "correct", source = "correct")
    Answer toAnswer(AnswerDto answerDto);

    default Question mapQuestion(int questionId) {
        return Question.builder().id(questionId).build();
    }
}
