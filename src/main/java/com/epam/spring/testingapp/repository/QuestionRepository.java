package com.epam.spring.testingapp.repository;

import com.epam.spring.testingapp.model.Question;
import com.epam.spring.testingapp.model.RunningTest;
import com.epam.spring.testingapp.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("select q from Question q " +
            "where q.test.id = :testId")
    Page<Question> findAllByTestId(Integer testId, Pageable pageable);
}
