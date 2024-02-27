package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.AnswerDto;
import com.epam.spring.testingapp.dto.group.OnUpdate;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.UnprocessableEntityException;
import com.epam.spring.testingapp.mapper.AccountMapper;
import com.epam.spring.testingapp.mapper.AnswerMapper;
import com.epam.spring.testingapp.model.Answer;
import com.epam.spring.testingapp.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test/question")
@Validated
@CrossOrigin
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("{questionId}/answer")
    public Set<AnswerDto> findAll(@PathVariable @Min(1) int questionId) {
        log.info("findAll({})", questionId);
        return AnswerMapper.INSTANCE.toAnswerDtos(answerService.findAll(questionId));
    }

    @GetMapping("/answer/{answerId}")
    public AnswerDto find(@PathVariable @Min(1) int answerId) {
        log.info("find({})", answerId);
        return AnswerMapper.INSTANCE.toAnswerDto(answerService.find(answerId));
    }

    @PostMapping("{questionId}/answer")
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerDto create(@RequestBody @Valid AnswerDto answerDto, @PathVariable @Min(1) int questionId) {
        log.info("create({}, {})", answerDto, questionId);
        Answer answer = AnswerMapper.INSTANCE.toAnswer(answerDto);

        try{
            answer = answerService.createForQuestion(answer, questionId);
        }catch (DataIntegrityViolationException e){
            throw new UnprocessableEntityException("Attempting to create existing answer with in question", e);
        }
        return AnswerMapper.INSTANCE.toAnswerDto(answerService.createForQuestion(answer, questionId));
    }

    @PutMapping("/answer/{answerId}")
    public AnswerDto update(@RequestBody @Validated(OnUpdate.class) AnswerDto answerDto, @PathVariable @Min(1) int answerId) {
        log.info("update({}, {})", answerDto, answerId);
        Answer answer = AnswerMapper.INSTANCE.toAnswer(answerDto);

        try{
            answer = answerService.update(answer, answerId);
        }catch (DataIntegrityViolationException e){
            throw new UnprocessableEntityException("Attempting to change answer description to one already exsisted in this question.", e);
        }

        return AnswerMapper.INSTANCE.toAnswerDto(answer);
    }

    @DeleteMapping("/answer/{answerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) int answerId) {
        log.info("delete({})", answerId);
        answerService.delete(answerId);
    }
}
