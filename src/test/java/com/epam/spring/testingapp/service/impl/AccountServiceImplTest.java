package com.epam.spring.testingapp.service.impl;

import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.UnprocessableEntityException;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.epam.spring.testingapp.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.epam.spring.testingapp.utils.getAccount;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountRepository accountRepository;

    private Account expectedAccount;
    private List<Account> expectedAccounts;


    @BeforeEach
    void setUp() {
        expectedAccount = getAccount();
        expectedAccounts = List.of(expectedAccount, getAccount());
    }

    @Test
    void findAll_ShouldReturnArrayOfAccounts() {
        when(accountRepository.findAllByAccountRole(any(), any(), any())).thenReturn(expectedAccounts);

        String search = "test";
        AccountRole role = AccountRole.USER;
        Pageable pageable = Pageable.unpaged();
        List<Account> actual = accountService.findAll(search, role, pageable);

        assertThat(actual).isEqualTo(expectedAccounts);
        verify(accountRepository, times(1)).findAllByAccountRole(search, role, pageable);
    }

    @Test
    void find_GivenAccountId_ShouldReturnAccount() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(expectedAccount));

        int accountId = expectedAccount.getId();
        Account actual = accountService.find(expectedAccount.getId());

        assertThat(actual).isEqualTo(expectedAccount);
        assertThat(actual.getId()).isEqualTo(expectedAccount.getId());
        verify(accountRepository, times(accountId)).findById(accountId);
    }

    @Test
    void find_GivenAccountId_ShouldThrowException() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());

        int accountId = expectedAccount.getId();
        assertThrows(NotFoundException.class, () -> {
            accountService.find(expectedAccount.getId());
        });

        verify(accountRepository, times(accountId)).findById(accountId);
    }

    @Test
    void create_GivenAccount_ShouldReturnCreatedAccount() {
        when(accountRepository.save(any())).thenReturn(expectedAccount);

        Account actual = accountService.register(expectedAccount);

        assertThat(actual).isEqualTo(expectedAccount);
        assertThat(actual.getId()).isEqualTo(expectedAccount.getId());
        verify(accountRepository, times(1)).save(expectedAccount);
    }

    @Test
    void create_GivenAccount_email_duplicate_ShouldThrowException() {
        when(accountRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(UnprocessableEntityException.class, () -> {
            accountService.register(expectedAccount);
        });

        verify(accountRepository, times(1)).save(any());
    }

    @Test
    void update_GivenAccountAndAccountId_ShouldReturnAccount() {
        when(accountRepository.save(any())).thenReturn(expectedAccount);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(expectedAccount));

        int accountId = expectedAccount.getId();
        Account actual = accountService.update(expectedAccount, accountId);

        assertThat(actual).isEqualTo(expectedAccount);
        assertThat(actual.getId()).isEqualTo(accountId);
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(expectedAccount);
    }

    @Test
    void update_GivenIdAndAccount_email_duplicate_ThenShouldThrowException() {
        when(accountRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(expectedAccount));

        int accountId = expectedAccount.getId();
        assertThrows(UnprocessableEntityException.class, () -> {
            accountService.update(expectedAccount, accountId);
        });

        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void update_GivenAccountAndAccountId_ShouldThrowException() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());

        int accountId = expectedAccount.getId();
        assertThrows(NotFoundException.class, () -> {
            accountService.update(expectedAccount, accountId);
        });

        verify(accountRepository, times(1)).findById(accountId);
    }
}