package com.epam.spring.testingapp.repository;

import com.epam.spring.testingapp.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Set<Answer> findAllByQuestion_Id(Integer question_id);
}
