package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.mapper.QuestionMapper;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.repository.QuestionRepository;
import com.epam.spring.testingapp.repository.TestRepository;
import com.epam.spring.testingapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    public final QuestionRepository questionRepository;
    public final TestRepository testRepository;

    @Override
    public List<Question> findAll(int testId) {
        List<Question> questions = questionRepository.findAllByTestId(testId);
        log.info("Founded questions = {}", questions);
        return questions;
    }

    @Override
    public Question find(int questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id %s not exist".formatted(questionId)));

        log.info("Founded question = {}", question);
        return question;
    }

    @Override
    @Transactional
    public Question createForTest(Question question, int testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("Test with id %s not exist".formatted(testId)));

        question.setTest(test);
        question = questionRepository.save(question);

        log.info("Created question = {}", question);
        return question;
    }

    @Override
    @Transactional
    public Question update(Question question, int questionId) {
        Question previousQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id %s not exist".formatted(questionId)));

        question.setId(questionId);
        question.setTest(previousQuestion.getTest());

        question = questionRepository.save(question);

        log.info("Updated question#{} to = {}", questionId, question);
        return question;
    }

    @Override
    @Transactional
    public void delete(int questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id %s not exist".formatted(questionId)));

        questionRepository.delete(question);
        log.info("Deleted question#{}", questionId);
    }
}
