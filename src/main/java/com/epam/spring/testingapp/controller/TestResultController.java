package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.service.RunningTestService;
import com.epam.spring.testingapp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
public class TestResultController {
    private final TestResultService testResultService;

    @GetMapping("/account/{accountId}/test-result")
    public List<TestResultDto> findAll(@PathVariable @Min(1) int accountId) {
        log.info("findAll({})", accountId);
        return testResultService.findAllByAccount(accountId);
    }
}
