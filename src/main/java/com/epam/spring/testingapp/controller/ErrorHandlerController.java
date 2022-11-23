package com.epam.spring.testingapp.controller;

import com.epam.spring.testingapp.dto.ErrorDto;
import com.epam.spring.testingapp.exception.NotFoundException;
import com.epam.spring.testingapp.exception.SuchEntityAlreadyExist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

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

    @ExceptionHandler(SuchEntityAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorDto handleSuchEntityAlreadyExist(SuchEntityAlreadyExist e) {
        log.warn("handleSuchEntityAlreadyExist with message={}", e.getMessage());
        return ErrorDto.builder().message(e.getMessage()).time(LocalDateTime.now()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("handleMethodArgumentNotValidException with message={}", e.getMessage());

        String message = "Validation failed: " + e.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ErrorDto.builder().message(message).time(LocalDateTime.now()).build();
    }
}
