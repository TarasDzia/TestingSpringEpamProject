package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.*;
import com.epam.spring.testingapp.model.UserAnswer;
import com.epam.spring.testingapp.service.QuestionService;
import com.epam.spring.testingapp.service.TestPassingService;
import com.epam.spring.testingapp.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/test")
public class PassingTestController {
    private final TestPassingService testPassingService;

    @PostMapping("/{testId}/passing")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void startTest(@PathVariable Integer testId, Principal accountId){
        testPassingService.startTest(testId, 1);
    }

    @PostMapping("/passing/answer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserAnswerDto addUserAnswer(@RequestBody UserAnswerDto userAnswerDto, Principal account){
        // TODO: 05.11.2022  if created new userAnswer than return 201 else return 200
        int accountId = 1;
        return testPassingService.addUserAnswer(userAnswerDto, accountId);
    }

    @GetMapping("/passing/question/{sequenceNumber}")
    public QuestionDto getQuestion(@PathVariable Integer sequenceNumber, Principal account) {
        log.info("getQuestion({},{})", sequenceNumber, account);
        return testPassingService.getQuestion(sequenceNumber, 1);
    }

}
