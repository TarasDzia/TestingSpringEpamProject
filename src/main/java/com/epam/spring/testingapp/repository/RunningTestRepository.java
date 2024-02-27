package com.epam.spring.testingapp.repository;

import com.epam.spring.testingapp.model.RunningTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RunningTestRepository extends JpaRepository<RunningTest, Integer> {
    @Query("Select rt FROM RunningTest rt " +
            "Where rt.account.id = :accountId and " +
            "rt.testResult is null")
    List<RunningTest> findAllNotFinished(Integer accountId);

    Optional<RunningTest> findFirstByAccount_IdAndTestResultNull(int accountId);

}
