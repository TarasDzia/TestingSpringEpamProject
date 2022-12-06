package com.epam.spring.testingapp.exception;

public class SequenceNumberOutOfBoundsException extends RuntimeException {
    public SequenceNumberOutOfBoundsException() {
    }

    public SequenceNumberOutOfBoundsException(String message) {
        super(message);
    }
}
