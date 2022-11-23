package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.mapper.QuestionMapper;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.repository.QuestionRepository;
import com.epam.spring.testingapp.repository.TestRepository;
import com.epam.spring.testingapp.service.QuestionService;
import com.epam.spring.testingapp.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    public final QuestionRepository questionRepository;
    public final TestRepository testRepository;

    @Override
    public List<QuestionDto> findAll(int testId) {
        List<Question> questions = questionRepository.findAllByTestId(testId);
        log.info("Founded questions = {}", questions);
        return QuestionMapper.INSTANCE.questionsToQuestionsDtos(questions);
    }

    @Override
    public QuestionDto find(int questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id %s not exist".formatted(questionId)));

        log.info("Founded question = {}", question);
        return QuestionMapper.INSTANCE.questionToQuestionDto(question);
    }

    @Override
    public QuestionDto createForTest(QuestionDto questionDto, int testId) {
        Question question = QuestionMapper.INSTANCE.questionDtoToQuestion(questionDto);

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));

        question.setTest(test);
        question = questionRepository.save(question);

        log.info("Created question = {}", question);
        return QuestionMapper.INSTANCE.questionToQuestionDto(question);
    }

    @Override
    public QuestionDto update(QuestionDto questionDto, int questionId) {
        questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id %s not exist".formatted(questionId)));

        Question question = QuestionMapper.INSTANCE.questionDtoToQuestion(questionDto);
        question.setId(questionId);

        question = questionRepository.save(question);

        log.info("Updated question#{} to = {}", questionId, question);
        return QuestionMapper.INSTANCE.questionToQuestionDto(question);
    }

    @Override
    public void delete(int questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id %s not exist".formatted(questionId)));

        questionRepository.delete(question);
        log.info("Deleted question#{}", questionId);
    }
}
