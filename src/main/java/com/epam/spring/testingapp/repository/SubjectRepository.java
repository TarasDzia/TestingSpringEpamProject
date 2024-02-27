package com.epam.spring.testingapp.repository;

import com.epam.spring.testingapp.model.Subject;
import com.epam.spring.testingapp.model.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Query("select s from Subject s" +
            " where" +
            " (:search = '' or s.name like %:search%)")
    Page<Subject> findAll(String search, Pageable pageable);
}
