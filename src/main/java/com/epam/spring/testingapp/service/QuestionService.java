package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.Question;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService {
    List<Question> findAll(int testId, Pageable pageable);

    Question find(int questionId);

    Question createForTest(Question questionDto, int testId);

    Question update(Question question, int questionId);

    void delete(int questionId);
}
