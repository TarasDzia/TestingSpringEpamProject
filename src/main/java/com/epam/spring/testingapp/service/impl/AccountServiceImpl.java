package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.UnprocessableEntityException;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.epam.spring.testingapp.repository.AccountRepository;
import com.epam.spring.testingapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("{account.error.password}")
    private String ERROR_PASSWORD_NOT_MATCH = "The password does not match the current one belonging to the account";

    @Override
    public List<Account> findAll(String search, AccountRole role, Pageable pageable) {
        search = Objects.isNull(search) ? "" : search;
        List<Account> accounts = accountRepository.findAllByAccountRole(search, role, pageable).getContent();

        log.info("Founded accounts =  {}", accounts);
        return accounts;
    }

    @Override
    public Page<Account> findAllPage(String search, AccountRole role, Pageable pageable) {
        search = Objects.isNull(search) ? "" : search;
        Page<Account> accounts = accountRepository.findAllByAccountRole(search, role, pageable);

        log.info("Founded accounts =  {}", accounts.getContent());
        return accounts;
    }

    @Override
    public Account find(int accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id %s not found".formatted(accountId)));

        log.info("Founded account =  {}", account);
        return account;
    }

    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Attempting to find Account with username = {}", username);
        Account account = accountRepository.findAccountByEmail(username)
                .orElseThrow(() -> new NotFoundException("Account with email %s not found".formatted(username)));
        log.info("Founded account by email =  {}", account);
        return account;
    }

    @Override
    public Account register(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        try {
            account = accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            throw new UnprocessableEntityException("Account with this email already exist", e);
        }

        log.info("Register account =  {}", account);
        return account;
    }

    @Override
    @Transactional
    public Account update(Account updateData, int accountId) {
        Account previousAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id %s not found".formatted(accountId)));


        previousAccount.setBirthdate(updateData.getBirthdate());
        previousAccount.setFirstname(updateData.getFirstname());
        previousAccount.setSurname(updateData.getSurname());
        previousAccount.setAccountRole(updateData.getAccountRole());

        try {
            previousAccount = accountRepository.save(previousAccount);
        } catch (DataIntegrityViolationException e) {
            throw new UnprocessableEntityException("Account failed update", e);
        }

        log.info("Updated {}", previousAccount);
        return previousAccount;
    }

    @Override
    public Account updateProfile(Account updateData, String email) {
        Account account = accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new NotFoundException("Profile with such email=%s doesn't exist".formatted(email)));

        account.setFirstname(updateData.getFirstname());
        account.setSurname(updateData.getSurname());
        account.setBirthdate(updateData.getBirthdate());

        account = accountRepository.save(account);

        return account;
    }

    @Override
    public void changePassword(String previousPassword, String newPassword, String email) {
        Account account = accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new NotFoundException("Account with email %s not found".formatted(email)));

        String encodedPassword = passwordEncoder.encode(previousPassword);

        if (!account.getPassword().equals(encodedPassword)) {
            log.info("Account(email={}) password wasn't change cause old password dont match", account.getEmail());
            throw new UnprocessableEntityException(ERROR_PASSWORD_NOT_MATCH);
        }

        account.setPassword(passwordEncoder.encode(newPassword));
        try{
            accountRepository.save(account);
            log.info("Account(email={}) password changed", account.getEmail());
        }catch (Exception e){
            log.error("Account password wasn't change!", e);
        }
    }

    @Override
    @Transactional
    public boolean switchBlock(int accountId) {
        Account previousAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id %s not found".formatted(accountId)));

        boolean previousAccountBanned = previousAccount.getBanned();

        previousAccount.setBanned(!previousAccountBanned);

        Account account = accountRepository.save(previousAccount);
        log.info("Updated blocked from {} => {}", previousAccountBanned, account.getBanned());

        return account.getBanned();
    }
}
