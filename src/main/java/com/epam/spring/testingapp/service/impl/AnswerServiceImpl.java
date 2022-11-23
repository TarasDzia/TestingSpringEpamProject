package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.mapper.AnswerMapper;
import com.epam.spring.testingapp.model.Answer;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.repository.AnswerRepository;
import com.epam.spring.testingapp.repository.QuestionRepository;
import com.epam.spring.testingapp.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    public final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    @Override
    public List<AnswerDto> findAll(int questionId) {
        List<Answer> answers = answerRepository.findAllByQuestion_Id(questionId);
        log.info("Founded answers from question#{} =  {}", questionId, answers);
        return AnswerMapper.INSTANCE.answersToAnswersDtos(answers);
    }

    @Override
    public AnswerDto find(int answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NotFoundException("Answer with id %s not exist".formatted(answerId)));

        log.info("Founded answer = {}", answer);
        return AnswerMapper.INSTANCE.answerToAnswerDto(answer);
    }

    @Override
    public AnswerDto createForQuestion(AnswerDto answerDto, int questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id %s not exist".formatted(questionId)));

        Answer answer = AnswerMapper.INSTANCE.answerDtoToAnswer(answerDto);
        answer.setQuestion(question);

        answer = answerRepository.save(answer);
        log.info("Created answer = {}", answer);
        return AnswerMapper.INSTANCE.answerToAnswerDto(answer);
    }

    @Override
    public AnswerDto update(AnswerDto answerDto, int answerId) {
        answerRepository.findById(answerId)
                .orElseThrow(() -> new NotFoundException("Answer with id %s not exist".formatted(answerId)));

        Answer answer = AnswerMapper.INSTANCE.answerDtoToAnswer(answerDto);
        answerDto.setId(answerId);

        answerRepository.save(answer);
        log.info("Updated answer#{} to = {}", answerId, answer);
        return AnswerMapper.INSTANCE.answerToAnswerDto(answer);
    }

    @Override
    public void delete(int answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NotFoundException("Answer with id %s not exist".formatted(answerId)));

        answerRepository.delete(answer);
        log.info("Deleted answer#{}", answerId);
    }
}
