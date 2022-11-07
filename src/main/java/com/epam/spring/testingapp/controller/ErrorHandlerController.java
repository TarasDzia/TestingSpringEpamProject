package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.ErrorDto;
import com.epam.spring.testingapp.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@Validated
class ErrorHandlerController {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("handleConstraintViolationException with message={}", e.getMessage());
        return ErrorDto.builder().message(e.getMessage()).time(LocalDateTime.now()).build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDto handleNotFoundException(NotFoundException e) {
        log.warn("handleNotFoundException with message={}", e.getMessage());
        return ErrorDto.builder().message(e.getMessage()).time(LocalDateTime.now()).build();
    }
}
