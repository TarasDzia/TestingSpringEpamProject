package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.repository.SubjectRepository;
import com.epam.spring.testingapp.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.epam.spring.testingapp.utils.getSubject;
import static com.epam.spring.testingapp.utils.getTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {
    @Mock
    private TestRepository testRepositoryMock;
    @Mock
    private SubjectRepository subjectRepositoryMock;

    @InjectMocks
    private TestServiceImpl testService;

    private Subject subject;
    private Test expectedTest;
    private List<Test> expectedTests;

    @BeforeEach
    void setUp() {
        subject = getSubject();
        expectedTest = getTest();
        expectedTest.setSubject(subject);
        expectedTests = List.of(expectedTest, getTest());
    }

    @org.junit.jupiter.api.Test
    void findAll_ShouldReturnListOfTests() {
        when(testRepositoryMock.findAllBySubjectId(anyString(), anyInt(), any())).thenReturn(expectedTests);

        String search = "search";
        int subjectId = 2;
        Pageable pageable = Pageable.unpaged();
        List<Test> actual = testService.findAll(search, subjectId, pageable);

        assertThat(actual).isEqualTo(expectedTests);
        verify(testRepositoryMock, times(1)).findAllBySubjectId(search, subjectId, pageable);
    }

    @org.junit.jupiter.api.Test
    void find_GivenTestId_ShouldReturnTest() {
        when(testRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(expectedTest));

        int testId = expectedTest.getId();
        Test actual = testService.find(testId);

        assertThat(actual).isEqualTo(expectedTest);
        assertThat(actual.getId()).isEqualTo(testId);
        verify(testRepositoryMock, times(1)).findById(testId);
    }

    @org.junit.jupiter.api.Test
    void update_GivenTestAndTestId_ShouldReturnTest() {
        when(testRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(expectedTest));
        when(testRepositoryMock.save(any())).thenReturn(expectedTest);

        int testId = expectedTest.getId();
        Test actual = testService.update(expectedTest, testId);

        assertThat(actual).isEqualTo(expectedTest);
        assertThat(actual.getId()).isEqualTo(testId);
        verify(testRepositoryMock, times(1)).findById(testId);
        verify(testRepositoryMock, times(1)).save(expectedTest);
    }

    @org.junit.jupiter.api.Test
    void update_GivenTestAndTestId_ShouldThrowException() {
        when(testRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        int testId = expectedTest.getId();
        assertThrows(NotFoundException.class, () -> {
            testService.update(expectedTest, testId);
        });

        verify(testRepositoryMock, times(1)).findById(testId);
    }

    @org.junit.jupiter.api.Test
    void create() {
        when(testRepositoryMock.save(any())).thenReturn(expectedTest);
        when(subjectRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(subject));

        int subjectId = expectedTest.getSubject().getId();
        Test actual = testService.create(expectedTest, subjectId);

        assertThat(actual).isEqualTo(expectedTest);
        assertThat(actual.getId()).isEqualTo(expectedTest.getId());
        verify(subjectRepositoryMock, times(1)).findById(subjectId);
        verify(testRepositoryMock, times(1)).save(expectedTest);
    }

    @org.junit.jupiter.api.Test
    void delete_GivenTestId() {
        when(testRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(expectedTest));

        int testId = expectedTest.getId();
        testService.delete(testId);

        verify(testRepositoryMock, times(1)).findById(testId);
        verify(testRepositoryMock, times(1)).delete(expectedTest);
    }

    @org.junit.jupiter.api.Test
    void delete_GivenTestId_ShouldThrowException() {
        when(testRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        int testId = expectedTest.getId();
        assertThrows(NotFoundException.class, () -> {
            testService.delete(testId);
        });

        verify(testRepositoryMock, times(1)).findById(testId);
    }
}