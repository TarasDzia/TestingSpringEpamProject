package com.epam.spring.testingapp.exception;

import java.text.Format;

public class NoTestRunningForAccountException extends RuntimeException {
    public NoTestRunningForAccountException() {
    }

    public NoTestRunningForAccountException(String message) {
        super(message);
    }
}
