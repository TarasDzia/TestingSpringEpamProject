package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.model.Answer;
import com.epam.spring.testingapp.model.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    Set<AnswerDto> mapAnswerDtoSet(Set<Answer> answers);
    Set<Answer> mapAnswerSet(Set<AnswerDto> answerDtos);
    @Mapping(target = "questionId", source = "question.id")
    AnswerDto answerToAnswerDto(Answer answer);
    @Mapping(target = "question", source = "questionId")
    Answer answerDtoToAnswer(AnswerDto answerDto);

    default Question mapQuestion(int questionId) {
        return Question.builder().id(questionId).build();
    }
}
