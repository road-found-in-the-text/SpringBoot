package com.example.umc3_teamproject.exception;

public class ForumNotFoundException extends RuntimeException{
    public ForumNotFoundException() {
        super();
    }

    public ForumNotFoundException(String message) {
        super(message);
    }

    public ForumNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForumNotFoundException(Throwable cause) {
        super(cause);
    }
}
