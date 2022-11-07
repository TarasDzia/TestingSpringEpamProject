package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AccountDto;
import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.dto.QuestionDto;
import com.epam.spring.testingapp.dto.TestDto;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.service.AnswerService;
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
@RequestMapping("/test/question")
@Validated
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("{questionId}/answer")
    public List<AnswerDto> findAll(@PathVariable @Min(1) int questionId) {
        log.info("findAll({})", questionId);
        return answerService.findAll(questionId);
    }

    @GetMapping("/answer/{answerId}")
    public AnswerDto find(@PathVariable @Min(1) int answerId) {
        log.info("find({})", answerId);

        AnswerDto answerDto = answerService.find(answerId);
        if(Objects.isNull(answerDto)){
            throw new NotFoundException("Answer with id %s not found".formatted(answerId));
        }
        return answerDto;
    }

    @PostMapping("{questionId}/answer")
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerDto create(@RequestBody @Valid AnswerDto answerDto, @PathVariable @Min(1) int questionId) {
        log.info("create({}, {})", answerDto, questionId);
        return answerService.create(answerDto, questionId);
    }

    @PutMapping("/answer/{answerId}")
    public AnswerDto update(@RequestBody @Validated(OnUpdate.class) AnswerDto answerDto, @PathVariable @Min(1) int answerId) {
        log.info("update({}, {})", answerDto, answerId);

        if(Objects.isNull(answerService.find(answerId))){
            throw new NotFoundException("Answer with id %s not found".formatted(answerId));
        }
        return answerService.update(answerDto, answerId);
    }

    @DeleteMapping("/answer/{answerId}")
    public void delete(@PathVariable @Min(1) int answerId) {
        log.info("delete({})", answerId);

        if(Objects.isNull(answerService.find(answerId))){
            throw new NotFoundException("Answer with id %s not found".formatted(answerId));
        }
        answerService.delete(answerId);
    }
}
