package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    List<Account> findAll(String search, AccountRole role, Pageable pageable);

    Account find(int accountId);

    Account register(Account account);

    Account update(Account account, int accountId);
}
