package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.AccountDto;

import java.util.List;

public interface AccountService {
    List<AccountDto> findAll();

    AccountDto find(int accountId);

    AccountDto register(AccountDto account);

    AccountDto update(AccountDto account, int accountId);
}
