package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.UnprocessableEntityException;
import com.epam.spring.testingapp.mapper.AccountMapper;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.epam.spring.testingapp.repository.AccountRepository;
import com.epam.spring.testingapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public List<Account> findAll(String search, AccountRole role, Pageable pageable) {
        search  = Objects.isNull(search)? "" : search;
        List<Account> accounts = accountRepository.findAllByAccountRole(search, role, pageable);

        log.info("Founded accounts =  {}", accounts);
        return accounts;
    }

    @Override
    public Account find(int accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->  new NotFoundException("Account with id %s not found".formatted(accountId)));

        log.info("Founded account =  {}", account);
        return account;
    }

    @Override
    public Account register(Account account) {
        try {
            account = accountRepository.save(account);
        }catch(DataIntegrityViolationException e ){
            throw new UnprocessableEntityException("Account with this email already exist", e);
        }

        log.info("Register account =  {}", account);
        return account;
    }

    @Override
    @Transactional
    public Account update(Account account, int accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() ->  new NotFoundException("Account with id %s not found".formatted(accountId)));

        account.setId(accountId);

        try {
            account = accountRepository.save(account);
        }catch(DataIntegrityViolationException e ){
            throw new UnprocessableEntityException("Account with this email already exist", e);
        }

        log.info("Updated {}", account);
        return account;
    }
}
