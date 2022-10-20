package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.mapper.QuestionMapper;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    @Override
    public List<QuestionDto> findAll(int testId) {
        List<Question> questions = List.of(
                Question.builder().id(1).test(Test.builder().id(testId).build()).build(),
                Question.builder().id(2).test(Test.builder().id(testId).build()).build());
        log.info("Founded questions = {}", questions);
        return QuestionMapper.INSTANCE.questionsToQuestionsDtos(questions);
    }

    @Override
    public QuestionDto find(int questionId) {
        Question question = Question.builder().id(questionId).build();
        log.info("Founded question = {}", question);
        return QuestionMapper.INSTANCE.questionToQuestionDto(question);
    }

    @Override
    public QuestionDto create(QuestionDto questionDto, int testId) {
        Question question = QuestionMapper.INSTANCE.questionDtoToQuestion(questionDto);
        question.setId(1);
        question.setTest(Test.builder().id(testId).build());
        log.info("Created question = {}", question);
        return QuestionMapper.INSTANCE.questionToQuestionDto(question);
    }

    @Override
    public QuestionDto update(QuestionDto questionDto, int questionId) {
        Question question = QuestionMapper.INSTANCE.questionDtoToQuestion(questionDto);
        question.setId(questionId);
        log.info("Updated question#{} to = {}", questionId, question);
        return QuestionMapper.INSTANCE.questionToQuestionDto(question);
    }

    @Override
    public void delete(int questionId) {
        log.info("Deleted question#{}", questionId);
    }
}
