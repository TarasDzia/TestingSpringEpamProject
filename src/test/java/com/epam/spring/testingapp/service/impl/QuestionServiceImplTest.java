package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.repository.QuestionRepository;
import com.epam.spring.testingapp.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.spring.testingapp.utils.getQuestion;
import static com.epam.spring.testingapp.utils.getTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {
    @Mock
    private TestRepository testRepositoryMock;

    @Mock
    private QuestionRepository questionRepositoryMock;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private Test test;
    private Question expectedQuestion;
    private List<Question> expectedQuestions;

    @BeforeEach
    void setUp() {
        test = getTest();
        expectedQuestion = getQuestion(test);
        expectedQuestions = List.of(expectedQuestion, getQuestion(test));
    }

    @org.junit.jupiter.api.Test
     void findAll_GivenTestId_ShouldReturnQuestionsList() {
        when(questionRepositoryMock.findAllByTestId(any())).thenReturn(expectedQuestions);

        int testId = expectedQuestion.getTest().getId();
        List<Question> actual = questionService.findAll(testId);

        assertThat(actual).isEqualTo(expectedQuestions);
        verify(questionRepositoryMock, times(1)).findAllByTestId(testId);
    }

    @org.junit.jupiter.api.Test
    void find_GivenQuestionId_ShouldReturnQuestion() {
        when(questionRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(expectedQuestion));

        int questionId = expectedQuestion.getId();
        Question actual = questionService.find(questionId);

        assertThat(actual).isEqualTo(expectedQuestion);
        assertThat(actual.getId()).isEqualTo(expectedQuestion.getId());
        verify(questionRepositoryMock, times(1)).findById(questionId);
    }

    @org.junit.jupiter.api.Test
    void find_GivenQuestionId_ShouldThrowException() {
        when(questionRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        int questionId = expectedQuestion.getId();
        assertThrows(NotFoundException.class, () -> {
            questionService.find(questionId);
        });
        verify(questionRepositoryMock, times(1)).findById(questionId);
    }

    @org.junit.jupiter.api.Test
    void createForTest_GivenQuestionAndTestId_ShouldReturnQuestion() {
        when(testRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(test));
        when(questionRepositoryMock.save(any())).thenReturn(expectedQuestion);

        int testId = expectedQuestion.getTest().getId();
        Question actual = questionService.createForTest(expectedQuestion, testId);

        assertThat(actual).isEqualTo(expectedQuestion);
        assertThat(actual.getId()).isEqualTo(expectedQuestion.getId());
        verify(testRepositoryMock, times(1)).findById(testId);
        verify(questionRepositoryMock, times(1)).save(expectedQuestion);
    }

    @org.junit.jupiter.api.Test
    void createForTest_GivenQuestionAndTestId_ShouldThrowException() {
        when(testRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        int testId = expectedQuestion.getTest().getId();
        assertThrows(NotFoundException.class, () -> {
            questionService.createForTest(expectedQuestion, testId);
        });

        verify(testRepositoryMock, times(1)).findById(testId);
    }

    @org.junit.jupiter.api.Test
    void update_GivenQuestionAndQuestionId_ShouldReturnQuestion() {
        when(questionRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(expectedQuestion));
        when(questionRepositoryMock.save(any())).thenReturn(expectedQuestion);

        int questionId = expectedQuestion.getId();
        Question actual = questionService.update(expectedQuestion, questionId);

        assertThat(actual).isEqualTo(expectedQuestion);
        assertThat(actual.getId()).isEqualTo(expectedQuestion.getId());
        verify(questionRepositoryMock, times(1)).findById(questionId);
        verify(questionRepositoryMock, times(1)).save(expectedQuestion);
    }

    @org.junit.jupiter.api.Test
    void update_GivenQuestionAndQuestionId_ShouldThrowException() {
        when(questionRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        int questionId = expectedQuestion.getId();
        assertThrows(NotFoundException.class, () -> {
            questionService.update(expectedQuestion, questionId);
        });

        verify(questionRepositoryMock, times(1)).findById(questionId);
    }

    @org.junit.jupiter.api.Test
    void delete_GivenQuestionId() {
        when(questionRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(expectedQuestion));

        int questionId = expectedQuestion.getId();
        questionService.delete(questionId);

        verify(questionRepositoryMock, times(1)).findById(questionId);
        verify(questionRepositoryMock, times(1)).delete(expectedQuestion);
    }

    @org.junit.jupiter.api.Test
    void delete_GivenQuestionId_ShouldThrowException() {
        when(questionRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        int questionId = expectedQuestion.getId();
        assertThrows(NotFoundException.class, () -> {
            questionService.delete(questionId);
        });

        verify(questionRepositoryMock, times(1)).findById(questionId);
    }
}