package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.group.OnCreate;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.mapper.AccountMapper;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.epam.spring.testingapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Validated
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public List<AccountDto> findAll(@RequestParam(required = false) String search, @RequestParam(required = false, name = "role") AccountRole accountRole,
                                    Pageable pageable) {
        log.info("findAll({},{},{})", search, accountRole, pageable);
        return AccountMapper.INSTANCE.toAccountDtos(accountService.findAll(search, accountRole, pageable));
    }

    @GetMapping("/{accountId}")
    public AccountDto find(@PathVariable @Min(1) int accountId) {
        log.info("find({})", accountId);
        return AccountMapper.INSTANCE.toAccountDto(accountService.find(accountId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto register(@RequestBody @Validated(OnCreate.class) AccountDto accountDto) {
        log.info("register({})", accountDto);
        Account account = AccountMapper.INSTANCE.toAccount(accountDto);
        return AccountMapper.INSTANCE.toAccountDto(accountService.register(account));
    }

    @PutMapping("/{accountId}")
    public AccountDto update(@RequestBody @Validated(OnUpdate.class) AccountDto accountDto, @PathVariable @Min(1) int accountId) {
        log.info("update({}, {})", accountDto, accountId);
        Account account = AccountMapper.INSTANCE.toAccount(accountDto);
        return AccountMapper.INSTANCE.toAccountDto(accountService.update(account, accountId));
    }
}
