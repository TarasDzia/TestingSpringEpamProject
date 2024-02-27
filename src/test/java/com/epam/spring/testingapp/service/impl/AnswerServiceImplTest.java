package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.model.Answer;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.repository.AnswerRepository;
import com.epam.spring.testingapp.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static com.epam.spring.testingapp.utils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerServiceImplTest {

    @Mock
    AnswerRepository answerRepository;

    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    AnswerServiceImpl answerService;

    private Question question;
    private Answer expectedAnswer;
    private Set<Answer> expectedAnswers;

    @BeforeEach
    void setUp() {
        question = getQuestion(getTest());
        expectedAnswer = getCorrectAnswer(question);
        expectedAnswers = Set.of(expectedAnswer, getBadAnswer(question));
    }

    @Test
    void findAll_GivenQuestionId_ShouldReturnSetOfAnswers() {
        int questionId = expectedAnswer.getQuestion().getId();
        when(answerRepository.findAllByQuestion_Id(questionId)).thenReturn(expectedAnswers);

        Set<Answer> actual = answerService.findAll(questionId);

        assertThat(actual).isEqualTo(expectedAnswers);
        verify(answerRepository, times(1)).findAllByQuestion_Id(questionId);
    }

    @Test
    void find_GivenAnswerId_ShouldReturnAnswer() {
        when(answerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(expectedAnswer));

        int answerId = expectedAnswer.getId();
        Answer actual = answerService.find(answerId);

        assertThat(actual).isEqualTo(expectedAnswer);
        assertThat(actual.getId()).isEqualTo(expectedAnswer.getId());
        verify(answerRepository, times(1)).findById(answerId);
    }

    @Test
    void find_GivenAnswerId_ShouldThrowException() {
        when(answerRepository.findById(anyInt())).thenReturn(Optional.empty());

        int answerId = expectedAnswer.getId();
        assertThrows(NotFoundException.class, () -> {
            answerService.find(answerId);
        });

        verify(answerRepository, times(1)).findById(answerId);
    }

    @Test
    void createForQuestion_GivenAnswerAndQuestionId_ShouldReturnCreatedAnswer() {
        when(answerRepository.save(any())).thenReturn(expectedAnswer);
        when(questionRepository.findById(any())).thenReturn(Optional.ofNullable(question));

        int questionId = question.getId();
        Answer actual = answerService.createForQuestion(expectedAnswer, questionId);

        assertThat(actual).isEqualTo(expectedAnswer);
        assertThat(actual.getId()).isEqualTo(expectedAnswer.getId());
        verify(questionRepository, times(1)).findById(questionId);
        verify(answerRepository, times(1)).save(expectedAnswer);
    }

    @Test
    void createForQuestion_GivenAnswerAndQuestionId_ShouldThrowException() {
        when(questionRepository.findById(any())).thenReturn(Optional.empty());

        int expectedQuestionId = question.getId();
        assertThrows(NotFoundException.class, () -> {
            answerService.createForQuestion(expectedAnswer, expectedQuestionId);
        });

        verify(questionRepository, times(1)).findById(expectedQuestionId);
    }

    @Test
    void update_GivenAnswerAndAnswerId_ShouldReturnAnswer() {
        when(answerRepository.save(any())).thenReturn(expectedAnswer);
        when(answerRepository.findById(any())).thenReturn(Optional.of(expectedAnswer));

        Integer updateId = expectedAnswer.getId();

        Answer actual = answerService.update(expectedAnswer, updateId);

        assertThat(actual.getId()).isEqualTo(updateId);
        assertThat(actual).isEqualTo(expectedAnswer);
        verify(answerRepository, times(1)).findById(updateId);
        verify(answerRepository, times(1)).save(expectedAnswer);
    }

    @Test
    void update_GivenAnswerAndAnswerId_ShouldThrowException() {
        when(answerRepository.findById(anyInt())).thenReturn(Optional.empty());

        Integer updateId = expectedAnswer.getId();

        assertThrows(NotFoundException.class, () -> {
            answerService.update(expectedAnswer, updateId);
        });
        verify(answerRepository, times(1)).findById(updateId);
    }

    @Test
    void delete_GivenAnswerId() {
        when(answerRepository.findById(any())).thenReturn(Optional.of(expectedAnswer));

        Integer deleteId = expectedAnswer.getId();
        answerService.delete(deleteId);

        verify(answerRepository, times(1)).findById(deleteId);
        verify(answerRepository, times(1)).delete(expectedAnswer);
    }

    @Test
    void delete_GivenAnswerId_ShouldThrowException() {
        when(answerRepository.findById(any())).thenReturn(Optional.empty());

        Integer deleteId = expectedAnswer.getId();

        assertThrows(NotFoundException.class, () -> {
            answerService.delete(deleteId);
        });

        verify(answerRepository, times(1)).findById(deleteId);
    }
}