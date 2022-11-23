package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    List<AccountDto> findAll(String search, AccountRole role, Pageable pageable);

    AccountDto find(int accountId);

    AccountDto register(AccountDto account);

    AccountDto update(AccountDto account, int accountId);
}
