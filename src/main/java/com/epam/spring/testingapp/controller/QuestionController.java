package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.mapper.QuestionMapper;
import com.epam.spring.testingapp.model.Question;
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
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("{testId}/question")
    public List<QuestionDto> findAll(@PathVariable @Min(1) int testId) {
        log.info("findAll({})", testId);
        return QuestionMapper.INSTANCE.mapQuestionsDtos(questionService.findAll(testId));
    }

    @GetMapping("/question/{questionId}")
    public QuestionDto find(@PathVariable @Min(1) int questionId) {
        log.info("find({})", questionId);
        return QuestionMapper.INSTANCE.mapQuestionDto(questionService.find(questionId));
    }

    @PostMapping("{testId}/question")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDto create(@RequestBody @Valid QuestionDto questionDto, @PathVariable @Min(1) int testId) {
        log.info("create({}, {})", questionDto, testId);
        Question question = QuestionMapper.INSTANCE.mapQuestion(questionDto);
        return QuestionMapper.INSTANCE.mapQuestionDto(questionService.createForTest(question, testId));
    }

    @PutMapping("/question/{questionId}")
    public QuestionDto update(@RequestBody @Validated(OnUpdate.class) QuestionDto questionDto, @PathVariable @Min(1) int questionId) {
        log.info("update({}, {})", questionDto, questionId);
        Question question = QuestionMapper.INSTANCE.mapQuestion(questionDto);
        return QuestionMapper.INSTANCE.mapQuestionDto(questionService.update(question, questionId));
    }

    @DeleteMapping("/question/{questionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) int questionId) {
        log.info("delete({})", questionId);
        questionService.delete(questionId);
    }
}
