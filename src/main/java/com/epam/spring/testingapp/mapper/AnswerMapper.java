package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.model.Answer;
import com.epam.spring.testingapp.model.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    List<AnswerDto> answersToAnswersDtos(List<Answer> answers);
    List<Answer> answersDtosToAnswers(List<AnswerDto> answerDtos);
    @Mapping(target = "questionId", source = "answer.question.id")
    AnswerDto answerToAnswerDto(Answer answer);
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "id", ignore = true)
    Answer answerDtoToAnswer(AnswerDto answerDto);

    default Question mapQuestion(int questionId) {
        return Question.builder().id(questionId).build();
    }
}
