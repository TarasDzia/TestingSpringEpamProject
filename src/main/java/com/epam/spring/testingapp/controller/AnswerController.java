package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test/question")
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("{questionId}/answer")
    public List<AnswerDto> findAll(@PathVariable int questionId) {
        return answerService.findAll(questionId);
    }

    @GetMapping("/answer/{answerId}")
    public AnswerDto find(@PathVariable int answerId) {
        return answerService.find(answerId);
    }

    @PostMapping("{questionId}/answer")
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerDto create(@RequestBody AnswerDto answerDto, @PathVariable int questionId) {
        return answerService.create(answerDto, questionId);
    }

    @PutMapping("/answer/{answerId}")
    public AnswerDto update(@RequestBody AnswerDto answerDto, @PathVariable int answerId) {
        return answerService.update(answerDto, answerId);
    }

    @DeleteMapping("/answer/{answerId}")
    public void delete(@PathVariable int answerId) {
        answerService.delete(answerId);
    }
}
