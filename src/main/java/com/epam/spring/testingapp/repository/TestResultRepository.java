package com.epam.spring.testingapp.repository;

import com.epam.spring.testingapp.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Integer> {
    List<TestResult> findByAccount_Id(int account_id);
}
