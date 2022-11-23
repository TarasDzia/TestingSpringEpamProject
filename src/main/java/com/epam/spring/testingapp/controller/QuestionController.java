package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.service.QuestionService;
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
@RequestMapping("/test")
@Validated
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("{testId}/question")
    public List<QuestionDto> findAll(@PathVariable @Min(1) int testId) {
        log.info("findAll({})", testId);
        return questionService.findAll(testId);
    }

    @GetMapping("/question/{questionId}")
    public QuestionDto find(@PathVariable @Min(1) int questionId) {
        log.info("find({})", questionId);
        return questionService.find(questionId);
    }

    @PostMapping("{testId}/question")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDto create(@RequestBody @Valid QuestionDto questionDto, @PathVariable @Min(1) int testId) {
        log.info("create({}, {})", questionDto, testId);
        return questionService.createForTest(questionDto, testId);
    }

    @PutMapping("/question/{questionId}")
    public QuestionDto update(@RequestBody @Validated(OnUpdate.class) QuestionDto questionDto, @PathVariable @Min(1) int questionId) {
        log.info("update({}, {})", questionDto, questionId);
        return questionService.update(questionDto, questionId);
    }

    @DeleteMapping("/question/{questionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) int questionId) {
        log.info("delete({})", questionId);
        questionService.delete(questionId);
    }
}
