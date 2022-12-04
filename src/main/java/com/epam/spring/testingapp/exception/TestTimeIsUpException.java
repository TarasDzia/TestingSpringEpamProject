package com.epam.spring.testingapp.exception;


public class TestTimeIsUpException extends RuntimeException {
    public TestTimeIsUpException(String message) {
        super(message);
    }

    public TestTimeIsUpException() {
        super();
    }
}
