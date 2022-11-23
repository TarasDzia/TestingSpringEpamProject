package com.epam.spring.testingapp.exception;

public class UnprocessableEntityException extends RuntimeException{
    public UnprocessableEntityException() {
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }
}
