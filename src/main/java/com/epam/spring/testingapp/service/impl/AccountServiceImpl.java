package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.SuchEntityAlreadyExist;
import com.epam.spring.testingapp.mapper.AccountMapper;
import com.epam.spring.testingapp.mapper.TestMapper;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.Test;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.epam.spring.testingapp.repository.AccountRepository;
import com.epam.spring.testingapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public List<AccountDto> findAll(String search, AccountRole role, Pageable pageable) {
        search  = Objects.isNull(search)? "" : search;
        List<Account> accounts = accountRepository.findAllByAccountRole(search, role, pageable);

        log.info("Founded accounts =  {}", accounts);
        return AccountMapper.INSTANCE.accountsToAccountDtos(accounts);
    }

    @Override
    public AccountDto find(int accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->  new NotFoundException("Account with id %s not found".formatted(accountId)));

        log.info("Founded account =  {}", account);
        return AccountMapper.INSTANCE.accountToAccountDto(account);
    }

    @Override
    public AccountDto register(AccountDto accountDto) {
        Account account = AccountMapper.INSTANCE.accountDtoToAccount(accountDto);

        try {
            account = accountRepository.save(account);
        }catch(DataIntegrityViolationException e ){
            throw new SuchEntityAlreadyExist("Account with this email already exist", e);
        }

        log.info("Register account =  {}", account);
        return AccountMapper.INSTANCE.accountToAccountDto(account);
    }

    @Override
    public AccountDto update(AccountDto accountDto, int accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() ->  new NotFoundException("Account with id %s not found".formatted(accountId)));

        Account account = AccountMapper.INSTANCE.accountDtoToAccount(accountDto);
        account.setId(accountId);

        try {
            account = accountRepository.save(account);
        }catch(DataIntegrityViolationException e ){
            throw new SuchEntityAlreadyExist("Account with this email already exist", e);
        }

        log.info("Updated {}", account);
        return AccountMapper.INSTANCE.accountToAccountDto(account);
    }
}
