package com.epam.spring.testingapp.service;

import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.List;

public interface AccountService extends UserDetailsService {
    List<Account> findAll(String search, AccountRole role, Pageable pageable);

    Page<Account> findAllPage(String search, AccountRole role, Pageable pageable);

    Account find(int accountId);

    Account register(Account account);

    Account update(Account account, int accountId);
    Account updateProfile(Account updateData, String email);

    void changePassword(String previousPassword, String newPassword, String email);

    boolean switchBlock(int accountId);
}
