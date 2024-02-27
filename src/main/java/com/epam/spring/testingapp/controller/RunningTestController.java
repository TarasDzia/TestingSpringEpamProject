package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.*;
import com.epam.spring.testingapp.mapper.QuestionMapper;
import com.epam.spring.testingapp.mapper.RunningTestMapper;
import com.epam.spring.testingapp.mapper.TestResultMapper;
import com.epam.spring.testingapp.service.RunningTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@CrossOrigin
public class RunningTestController {
    private final RunningTestService runningTestService;

    @PostMapping("/{testId}/passing")
    @ResponseStatus(code = HttpStatus.CREATED)
    public RunningTestDto startTest(@PathVariable @Min(1) Integer testId, @RequestParam @Min(1) int accountId, Principal account){
        //      todo Take current account from security.
        log.info("startTest(testId={},accountId={})", testId, accountId);
        return RunningTestMapper.INSTANCE.toRunningTestDto(runningTestService.start(testId, accountId));
    }
    @GetMapping("/passing")
    public RunningTestDto findRunningTest(@RequestParam @Min(1) int accountId, Principal account){
        //      todo Take current account from security.
        log.info("findRunningTest(accountId={})", accountId);

        return RunningTestMapper.INSTANCE.toRunningTestDto(runningTestService.findCurrent(accountId));
    }

    @PatchMapping("/passing/answer")
    public RunningTestDto addChosenAnswers(@RequestBody @NotEmpty Set<Integer> choosenAnswersIds, @RequestParam @Min(1) int accountId, Principal account){
//      TODO Take current account from security.
        log.info("addChosenAnswers(answers={}, accountId={})", choosenAnswersIds, accountId);
        return RunningTestMapper.INSTANCE.toRunningTestDto(runningTestService.addUserAnswer(choosenAnswersIds, accountId));
    }

    @GetMapping("/passing/question/")
    public Page<QuestionDto> getQuestions(@RequestParam @Min(1) int accountId, Pageable pageable , Principal account){
//      TODO Take current account from security.
        log.info("getQuestions({},{})", accountId, pageable);
        return QuestionMapper.INSTANCE.toQuestionsDtoPage(runningTestService.getQuestion(pageable, accountId));
    }

    @PostMapping("/passing/finish")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TestResultDto finishTest(@RequestParam @Min(1) int accountId, Principal account){
//      TODO Take current account from security.
        log.info("finishTest({})", accountId);
        return TestResultMapper.INSTANCE.toTestResultDto(runningTestService.finish(accountId));
    }

}
