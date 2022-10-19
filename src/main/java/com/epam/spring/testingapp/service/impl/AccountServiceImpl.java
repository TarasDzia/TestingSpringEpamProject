package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.RegisterDTO;
import com.epam.spring.testingapp.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public List<AccountDto> findAll() {
        return null;
    }

    @Override
    public AccountDto find(int accountId) {
        return null;
    }

    @Override
    public AccountDto register(RegisterDTO account) {
        return null;
    }

    @Override
    public AccountDto update(AccountDto account, int accountId) {
        return null;
    }
}
