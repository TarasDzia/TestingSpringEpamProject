package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.*;
import com.epam.spring.testingapp.mapper.QuestionMapper;
import com.epam.spring.testingapp.mapper.RunningTestMapper;
import com.epam.spring.testingapp.mapper.TestResultMapper;
import com.epam.spring.testingapp.service.RunningTestService;
import com.epam.spring.testingapp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.security.Principal;
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/test")
public class RunningTestController {
    private final RunningTestService runningTestService;

    @PostMapping("/{testId}/passing")
    @ResponseStatus(code = HttpStatus.CREATED)
    public RunningTestDto startTest(@PathVariable @Min(1) Integer testId, @RequestParam @Min(1) int accountId, Principal account){
        //      todo Take current account from security.
        log.info("startTest(testId={},accountId={})", testId, accountId);
        return RunningTestMapper.INSTANCE.toRunningTestDto(runningTestService.startTest(testId, accountId));
    }

    @PatchMapping("/passing/answer")
    public RunningTestDto addChosenAnswers(@RequestBody @NotEmpty Set<Integer> choosenAnswersIds, @RequestParam @Min(1) int accountId, Principal account){
//      todo Take current account from security.
        log.info("addChosenAnswers(answers={}, accountId={})", choosenAnswersIds, accountId);
        return RunningTestMapper.INSTANCE.toRunningTestDto(runningTestService.addUserAnswer(choosenAnswersIds, accountId));
    }

    @GetMapping("/passing/question/{sequenceNumber}")
    public QuestionDto getQuestion(@PathVariable Integer sequenceNumber, @RequestParam int accountId, Principal account){
//      todo Take current account from security.
        log.info("getQuestion({},{})", sequenceNumber, accountId);
        return QuestionMapper.INSTANCE.mapDtoWithoutCorrect(runningTestService.getQuestion(sequenceNumber, accountId));
    }

    @PostMapping("/passing/finish")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TestResultDto finishTest(@RequestParam int accountId, Principal account){
//      todo Take current account from security.
        log.info("finishTest({})", accountId);
        return TestResultMapper.INSTANCE.toTestResultDto(runningTestService.finishTestById(accountId));
    }

}
