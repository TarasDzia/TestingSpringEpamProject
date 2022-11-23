package com.epam.spring.testingapp.exception;

public class SuchEntityAlreadyExist extends RuntimeException{
    public SuchEntityAlreadyExist() {
    }

    public SuchEntityAlreadyExist(String message) {
        super(message);
    }

    public SuchEntityAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }
}
