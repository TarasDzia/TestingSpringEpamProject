package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.UnprocessableEntityException;
import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static com.epam.spring.testingapp.utils.getSubject;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {
    @Mock
    SubjectRepository subjectRepositoryMock;

    @InjectMocks
    SubjectServiceImpl subjectService;

    private Subject expectedSubject;

    private List<Subject> expectedSubjects;

    @BeforeEach
    void setUp() {
        expectedSubject = getSubject();
        expectedSubjects = List.of(expectedSubject, getSubject());
    }


    @Test
    void findAll_ShouldReturnListOfSubjects() {
        when(subjectRepositoryMock.findAll()).thenReturn(expectedSubjects);

        List<Subject> actual = subjectService.findAll();

        assertThat(actual).isEqualTo(expectedSubjects);
        verify(subjectRepositoryMock, times(1)).findAll();
    }

    @Test
    void create_GivenSubject_ShouldReturnSubject() {
        when(subjectRepositoryMock.save(any())).thenReturn(expectedSubject);

        Subject actual = subjectService.create(expectedSubject);

        assertThat(actual).isEqualTo(expectedSubject);
        assertThat(actual.getId()).isEqualTo(expectedSubject.getId());
        verify(subjectRepositoryMock, times(1)).save(expectedSubject);
    }
    @Test
    void create_GivenSubject_duplicate_name_ShouldThrowException() {
        when(subjectRepositoryMock.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(UnprocessableEntityException.class, () -> {
            subjectService.create(expectedSubject);
        });

        verify(subjectRepositoryMock, times(1)).save(expectedSubject);
    }

    @Test
    void update_GivenSubjectAndSubjectId_ShouldReturnSubject() {
        when(subjectRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(expectedSubject));
        when(subjectRepositoryMock.save(any())).thenReturn(expectedSubject);

        int subjectId = expectedSubject.getId();
        Subject actual = subjectService.update(expectedSubject, subjectId);

        assertThat(actual).isEqualTo(expectedSubject);
        assertThat(actual.getId()).isEqualTo(expectedSubject.getId());
        verify(subjectRepositoryMock, times(1)).findById(subjectId);
        verify(subjectRepositoryMock, times(1)).save(expectedSubject);
    }

    @Test
    void update_GivenSubjectAndSubjectId_duplicate_name_ShouldThrowException() {
        when(subjectRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(expectedSubject));
        when(subjectRepositoryMock.save(any())).thenThrow(DataIntegrityViolationException.class);

        int subjectId = expectedSubject.getId();
        assertThrows(UnprocessableEntityException.class, () -> {
            subjectService.update(expectedSubject, subjectId);
        });

        verify(subjectRepositoryMock, times(1)).findById(subjectId);
        verify(subjectRepositoryMock, times(1)).save(expectedSubject);
    }

    @Test
    void update_GivenSubjectAndSubjectId_ShouldThrowException() {
        when(subjectRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        int subjectId = expectedSubject.getId();
        assertThrows(NotFoundException.class, () -> {
            subjectService.update(expectedSubject, subjectId);
        });

        verify(subjectRepositoryMock, times(1)).findById(subjectId);
    }

    @Test
    void delete_GivenSubjectId() {
        when(subjectRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(expectedSubject));

        int subjectId = expectedSubject.getId();
        subjectService.delete(subjectId);

        verify(subjectRepositoryMock, times(1)).findById(subjectId);
        verify(subjectRepositoryMock, times(1)).delete(expectedSubject);
    }

    @Test
    void delete_GivenSubjectId_ShouldThrowException() {
        when(subjectRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        int subjectId = expectedSubject.getId();
        assertThrows(NotFoundException.class, () -> {
            subjectService.delete(subjectId);
        });

        verify(subjectRepositoryMock, times(1)).findById(subjectId);
    }
}