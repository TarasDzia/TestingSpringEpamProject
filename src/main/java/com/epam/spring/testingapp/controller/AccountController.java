package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.RegisterDTO;
import com.epam.spring.testingapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public List<AccountDto> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{accountId}")
    public AccountDto find(@PathVariable int accountId) {
        return accountService.find(accountId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto register(@RequestBody RegisterDTO account) {
        return accountService.register(account);
    }

    @PutMapping("/{accountId}")
    public AccountDto update(@RequestBody AccountDto account, @PathVariable int accountId) {
        return accountService.update(account, accountId);
    }
}
