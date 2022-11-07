package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        QuestionDto questionDto = questionService.find(questionId);
        if(Objects.isNull(questionDto)){
            throw new NotFoundException("Question with id %s not found".formatted(questionId));
        }

        return questionDto;
    }

    @PostMapping("{testId}/question")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDto create(@RequestBody @Valid QuestionDto questionDto, @PathVariable @Min(1) int testId) {
        log.info("create({}, {})", questionDto, testId);
        return questionService.create(questionDto, testId);
    }

    @PutMapping("/question/{questionId}")
    public QuestionDto update(@RequestBody @Validated(OnUpdate.class) QuestionDto questionDto, @PathVariable @Min(1) int questionId) {
        log.info("update({}, {})", questionDto, questionId);

        if(Objects.isNull(questionService.find(questionId))){
            throw new NotFoundException("Question with id %s not found".formatted(questionId));
        }
        return questionService.update(questionDto, questionId);
    }

    @DeleteMapping("/question/{questionId}")
    public void delete(@PathVariable @Min(1) int questionId) {
        log.info("delete({})", questionId);

        if(Objects.isNull(questionService.find(questionId))){
            throw new NotFoundException("Question with id %s not found".formatted(questionId));
        }
        questionService.delete(questionId);
    }
}
