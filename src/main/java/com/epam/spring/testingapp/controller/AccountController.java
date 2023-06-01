package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.mapper.AccountMapper;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.epam.spring.testingapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Validated
@CrossOrigin
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/all")
    public List<AccountDto> findAllList(@RequestParam(required = false) String search,
                                        @RequestParam(required = false, name = "role") AccountRole accountRole,
                                    Pageable pageable) {
        log.info("findAllList({},{},{})", search, accountRole, pageable);
        return AccountMapper.INSTANCE.toAccountDtos(accountService.findAll(search, accountRole, pageable));
    }

    @GetMapping
    public Page<AccountDto> findPage(@RequestParam(required = false) String search,
                                     @RequestParam(required = false, name = "role") AccountRole accountRole,
                                     Pageable pageable) {
        log.info("findPage({},{},{})", search, accountRole, pageable);
        return AccountMapper.INSTANCE.toAccountDtos(accountService.findAllPage(search, accountRole, pageable));
    }

    @GetMapping("/{accountId}")
    public AccountDto find(@PathVariable @Min(1) int accountId) {
        log.info("find({})", accountId);
        return AccountMapper.INSTANCE.toAccountDto(accountService.find(accountId));
    }

    @PutMapping("/{accountId}")
    @Secured("ADMIN")
    public AccountDto update(@RequestBody @Validated(OnUpdate.class) AccountDto accountDto, @PathVariable @Min(1) int accountId, Authentication authentication) {
        log.info("update({}, {})", accountDto, accountId);
        Account account = AccountMapper.INSTANCE.toAccount(accountDto);
        return AccountMapper.INSTANCE.toAccountDto(accountService.update(account, accountId));
    }


    @PostMapping("/password")
    public void changePassword(@RequestParam(value = "password") @NotEmpty(message = "{notBlank.account.password}") String previousPassword,
                                     @RequestParam(value = "new_password") @Pattern(message = "{pattern.account.password}", regexp = "^\\w{8,}$") String newPassword,
                                     Principal principal) {
        log.info("changePassword({}, {}, {})", previousPassword, newPassword, principal.getName());

        accountService.changePassword(previousPassword, newPassword, principal.getName());
    }

    @PatchMapping ("/{accountId}")
    public ResponseEntity<Boolean> switchBlocked(@PathVariable @Min(1) int accountId) {
        log.info("switchBlocked({})", accountId);
        return ResponseEntity.ok(accountService.switchBlock(accountId));
    }
    @GetMapping("/me")
    public AccountDto getProfile (Principal principal) {
        log.info("Retrieve current logged is - {}", principal.getName());
        Account user = (Account) accountService.loadUserByUsername(principal.getName());
        return AccountMapper.INSTANCE.toAccountDto(user);
    }

    @PatchMapping("/me")
    public AccountDto updateProfile(@RequestBody @Validated(OnUpdate.class) AccountDto accountDto, Principal principal) {
        log.info("updateProfile({}, {})", accountDto, principal.getName());
        Account account = AccountMapper.INSTANCE.toAccount(accountDto);
        return AccountMapper.INSTANCE.toAccountDto(accountService.updateProfile(account, principal.getName()));
    }
}
