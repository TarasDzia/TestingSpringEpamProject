package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.RegisterDTO;

import java.util.List;

public interface AccountService {
    List<AccountDto> findAll();

    AccountDto find(int accountId);

    AccountDto register(RegisterDTO account);

    AccountDto update(AccountDto account, int accountId);
}
