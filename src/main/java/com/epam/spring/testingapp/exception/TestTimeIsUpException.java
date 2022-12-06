package com.epam.spring.testingapp.exception;


public class TestTimeIsUpException extends Exception {
    public TestTimeIsUpException(String message) {
        super(message);
    }

    public TestTimeIsUpException() {
        super();
    }
}
