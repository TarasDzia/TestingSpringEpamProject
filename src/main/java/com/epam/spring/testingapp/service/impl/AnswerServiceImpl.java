package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.UnprocessableEntityException;
import com.epam.spring.testingapp.model.Answer;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.repository.AnswerRepository;
import com.epam.spring.testingapp.repository.QuestionRepository;
import com.epam.spring.testingapp.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    public final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    @Override
    public Set<Answer> findAll(int questionId) {
        Set<Answer> answers = answerRepository.findAllByQuestion_Id(questionId);
        log.info("Founded answers from question#{} =  {}", questionId, answers);
        return answers;
    }

    @Override
    public Answer find(int answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NotFoundException("Answer with id %s not exist".formatted(answerId)));

        log.info("Founded answer = {}", answer);
        return answer;
    }

    @Override
    @Transactional
    public Answer createForQuestion(Answer answer, int questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id %s not exist".formatted(questionId)));

        answer.setQuestion(question);

        answer = answerRepository.save(answer);
        log.info("Created answer = {}", answer);
        return answer;
    }

    @Override
    @Transactional
    public Answer update(Answer answer, int answerId) {
        Answer previousAnswer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NotFoundException("Answer with id %s not exist".formatted(answerId)));

        answer.setId(answerId);
        answer.setQuestion(previousAnswer.getQuestion());

        answer = answerRepository.save(answer);
        log.info("Updated answer#{} to = {}", answerId, answer);
        return answer;
    }

    @Override
    @Transactional
    public void delete(int answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NotFoundException("Answer with id %s not exist".formatted(answerId)));

        answerRepository.delete(answer);
        log.info("Deleted answer#{}", answerId);
    }
}
