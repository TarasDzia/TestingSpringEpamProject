package com.epam.spring.testingapp.repository;

import com.epam.spring.testingapp.model.RunningTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import java.util.List;
import java.util.Optional;

@Repository
public interface RunningTestRepository extends JpaRepository<RunningTest, Integer> {
    // TODO: 23.11.2022 Must return Optional
//    @Query("Select rt from RunningTest rt join Test t on rt.test.id = t.id where rt.account.id = :accountId " +
//            "and current_timestamp < rt.startTime + cast( as double) t.duration*(24*60)" +
//            " order by rt.startTime desc")
//    List<RunningTest> findByAccountNotFinished(Integer accountId);
    @Query(value =
            "Select * FROM running_tests as rt inner join tests as t on rt.id = t.id\n" +
            "Where rt.account_id = 1 and " +
            "current_timestamp() < date_add(rt.start_time, interval t.duration minute)" +
            " order by rt.start_time desc"
            , nativeQuery = true)
    Optional<RunningTest> findByAccountNotFinished(Integer accountId);
}
