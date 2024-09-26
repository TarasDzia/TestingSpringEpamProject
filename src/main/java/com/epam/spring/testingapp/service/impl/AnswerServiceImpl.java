package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.mapper.AnswerMapper;
import com.epam.spring.testingapp.model.Answer;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AnswerServiceImpl implements AnswerService {
    @Override
    public List<AnswerDto> findAll(int questionId) {
        List<Answer> answers = List.of(Answer.builder().question(Question.builder().id(questionId).build()).build());
        log.info("Founded answers from question#{} =  {}", questionId, answers);
        return AnswerMapper.INSTANCE.answersToAnswersDtos(answers);
    }

    @Override
    public AnswerDto find(int answerId) {
        Answer answer = Answer.builder().id(answerId).build();
        log.info("Founded answer = {}", answer);
        return AnswerMapper.INSTANCE.answerToAnswerDto(answer);
    }

    @Override
    public AnswerDto create(AnswerDto answerDto, int questionId) {
        Answer answer = AnswerMapper.INSTANCE.answerDtoToAnswer(answerDto);
        answer.setQuestion(Question.builder().id(questionId).build());
        log.info("Created answer = {}", answer);
        return AnswerMapper.INSTANCE.answerToAnswerDto(answer);
    }

    @Override
    public AnswerDto update(AnswerDto answerDto, int answerId) {
        Answer answer = AnswerMapper.INSTANCE.answerDtoToAnswer(answerDto);
        answerDto.setId(answerId);
        log.info("Updated answer#{} to = {}", answerId, answer);
        return AnswerMapper.INSTANCE.answerToAnswerDto(answer);
    }

    @Override
    public void delete(int answerId) {
        log.info("Deleted answer#{}", answerId);
    }
}
