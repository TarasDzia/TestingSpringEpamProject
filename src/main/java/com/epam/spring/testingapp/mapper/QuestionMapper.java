package com.epam.spring.testingapp.mapper;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.SubjectDto;
import com.epam.spring.testingapp.model.Answer;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.model.Test;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

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
    QuestionDto mapQuestionDto(Question question);

    @Named("mapQuestion")
    @Mapping(target = "test", source = "testId")
    Question mapQuestion(QuestionDto questionDto);

    @Mapping(target = "answers", source = "answers", qualifiedByName="mapAnswersDtoWithoutCorrect")
    @Mapping(target = "multipleAnswers", source = "answers")
    @Mapping(target = "testId", source = "test.id")
    QuestionDto mapDtoWithoutCorrect(Question question);

    default Page<QuestionDto> toQuestionsDtoPage(Page<Question> page) {
        return page.map(this::mapDtoWithoutCorrect);
    }

    default Test mapTest(int id){
        return Test.builder().id(id).build();
    }
    default boolean mapMultipleAnswers(Set<Answer> answers){
        return answers.stream().filter(Answer::isCorrect).count() > 1;
    }
}
