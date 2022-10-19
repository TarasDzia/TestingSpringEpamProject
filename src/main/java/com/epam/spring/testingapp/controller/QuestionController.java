package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("{testId}/question")
    public List<QuestionDto> findAll(@PathVariable int testId) {
        return questionService.findAll(testId);
    }

    @GetMapping("/question/{questionId}")
    public QuestionDto find(@PathVariable int questionId) {
        return questionService.find(questionId);
    }

    @PostMapping("/question")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDto create(@RequestBody QuestionDto questionDto) {
        return questionService.create(questionDto);
    }

    @PutMapping("/question/{questionId}")
    public QuestionDto update(@RequestBody QuestionDto questionDto, @PathVariable int questionId) {
        return questionService.update(questionDto, questionId);
    }

    @DeleteMapping("/question/{questionId}")
    public void delete(@PathVariable int questionId) {
        questionService.delete(questionId);
    }
}
