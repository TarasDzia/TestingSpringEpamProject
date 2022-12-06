package com.epam.spring.testingapp.repository;

import com.epam.spring.testingapp.model.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    @Query("select t from Test t join Subject s on t.subject.id = s.id " +
            " where" +
            " (:subjectId is null or t.subject.id = :subjectId) and " +
            " (:search = '' or t.name like %:search%)")
    List<Test> findAllBySubjectId(String search, Integer subjectId, Pageable pageable);
}
