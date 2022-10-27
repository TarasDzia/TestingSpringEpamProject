package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.group.OnCreate;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Validated
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public List<AccountDto> findAll() {
        log.info("findAll()");
        return accountService.findAll();
    }

    @GetMapping("/{accountId}")
    public AccountDto find(@PathVariable @Min(1) int accountId) {
        log.info("find({})", accountId);
        return accountService.find(accountId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto register(@RequestBody @Validated(OnCreate.class) AccountDto account) {
        log.info("register({})", account);
        return accountService.register(account);
    }

    @PutMapping("/{accountId}")
    public AccountDto update(@RequestBody @Validated(OnUpdate.class) AccountDto account, @PathVariable @Min(1) int accountId) {
        log.info("update({}, {})", account, accountId);
        return accountService.update(account, accountId);
    }
}
