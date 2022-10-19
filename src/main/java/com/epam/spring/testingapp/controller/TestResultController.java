package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.TestResultDto;
import com.epam.spring.testingapp.dto.UserAnswerDto;
import com.epam.spring.testingapp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestResultController {
    private final TestResultService testResultService;

    @GetMapping("/account/{accountId}/test-result")
    public List<TestResultDto> findAll(@PathVariable int accountId) {
        log.info("findAll({})", accountId);
        return testResultService.findAllByAccount(accountId);
    }

    @PostMapping("test/{testId}/pass")
//    todo inject jwt principal object to get current user that passing test
    public TestResultDto passTest(@RequestBody List<UserAnswerDto> userAnswers, @PathVariable int testId, Principal principal) {
//        AccountDto principal = (AccountDto) principal;
        log.info("passTest({},{},{})", userAnswers, testId, principal);
        return testResultService.passTest(userAnswers, testId);
    }
}
