package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.UserAnswerDto;
import com.epam.spring.testingapp.model.*;
import jdk.jfr.Name;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface UserAnswerMapper {
    UserAnswerMapper INSTANCE = Mappers.getMapper(UserAnswerMapper.class);

    Set<UserAnswer> toEntities(Set<UserAnswerDto> userAnswers);

    Set<UserAnswerDto> toDtos(Set<UserAnswer> UserAnswers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "runningTest", ignore = true)
    @Mapping(target = "question", source = "questionId", qualifiedByName = "mapQuestion")
    @Mapping(target = "answers", source = "answerIds", qualifiedByName = "mapAnswerSet")
    UserAnswer toEntity(UserAnswerDto dto);
    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "answerIds", source = "answers", qualifiedByName = "mapAnswerIdsSet")
    UserAnswerDto toDto(UserAnswer entity);

    @Named("mapAnswerSet")
    @IterableMapping(qualifiedByName = "mapAnswer")
    Set<Answer> mapAnswerSet(Set<Integer> answerIds);

    @Named("mapAnswerIdsSet")
    @IterableMapping(qualifiedByName = "mapAnswerId")
    Set<Integer> mapAnswerIds(Set<Answer> userAnswers);

    @Named("mapQuestion")
    default Question mapQuestion(int questionId) {
        return Question.builder().id(questionId).build();
    }
    @Named("mapAnswerId")
    default int mapAnswer(Answer answer){
        return  answer.getId();
    }
    @Named("mapAnswer")
    default Answer mapAnswer(int id){
        return  Answer.builder().id(id).build();
    }
}
