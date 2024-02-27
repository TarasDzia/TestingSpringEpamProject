package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.RunningTestDto;
import com.epam.spring.testingapp.model.RunningTest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;

@Mapper(uses = {TestMapper.class,AnswerMapper.class})
public interface RunningTestMapper {
    RunningTestMapper INSTANCE = Mappers.getMapper(RunningTestMapper.class);
    @Mapping(target = "test", source = "test", qualifiedByName = "toDtoWithoutQuestions")
    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "userAnswers", source = "userAnswers", qualifiedByName = "mapAnswersDtoWithoutCorrect")
    @Mapping(target = "testResultId", source = "testResult.id")
    RunningTestDto toRunningTestDto(RunningTest runningTest);


}
