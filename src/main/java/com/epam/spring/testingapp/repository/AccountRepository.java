package com.epam.spring.testingapp.repository;

import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("select a from Account a" +
            " where" +
            " (:accountRole is null or a.accountRole = :accountRole) and " +
            " (:search = '' or a.email like %:search% or a.firstname like %:search% or a.surname like %:search%)")
    List<Account> findAllByAccountRole(String search, AccountRole accountRole, Pageable pageable);
//    or a.firstname like %:search% or a.surname like %:search%
}
