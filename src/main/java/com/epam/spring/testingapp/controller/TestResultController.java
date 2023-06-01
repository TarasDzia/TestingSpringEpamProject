package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.mapper.TestResultMapper;
import com.epam.spring.testingapp.model.Account;
import com.epam.spring.testingapp.model.enumerate.AccountRole;
import com.epam.spring.testingapp.service.AccountService;
import com.epam.spring.testingapp.service.RunningTestService;
import com.epam.spring.testingapp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class TestResultController {
    private final TestResultService testResultService;
    private final AccountService accountService;

    @GetMapping("/account/me/test-result")
    public Page<TestResultDto> findAll(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("findAll({}, {})", authentication != null ? authentication.getName():null, pageable);
        Account user = (Account) accountService.loadUserByUsername(authentication.getName());
        return TestResultMapper.INSTANCE.toTestResultsDtos(testResultService.findAllByAccount(user.getId(), pageable));
    }
}
