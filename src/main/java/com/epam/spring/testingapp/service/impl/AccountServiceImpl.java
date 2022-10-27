package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.mapper.AccountMapper;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public List<AccountDto> findAll() {
        List<Account> accounts = List.of(Account.builder().id(1).build(), Account.builder().id(2).build());
        log.info("Founded accounts =  {}", accounts);
        return AccountMapper.INSTANCE.accountsToAccountDtos(accounts);
    }

    @Override
    public AccountDto find(int accountId) {
        Account account = Account.builder().id(accountId).build();
        log.info("Founded account =  {}", account);
        return AccountMapper.INSTANCE.accountToAccountDto(account);
    }

    @Override
    public AccountDto register(AccountDto accountDto) {
        Account account = AccountMapper.INSTANCE.accountDtoToAccount(accountDto);

//        saving
        account.setId(1);

        log.info("Register account =  {}", account);
        return AccountMapper.INSTANCE.accountToAccountDto(account);
    }

    @Override
    public AccountDto update(AccountDto accountDto, int accountId) {
        Account account = AccountMapper.INSTANCE.accountDtoToAccount(accountDto);
        account.setId(accountId);
        log.info("Updated account =  {}", account);
        return AccountMapper.INSTANCE.accountToAccountDto(account);
    }
}
