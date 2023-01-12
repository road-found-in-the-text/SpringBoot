package com.example.umc3_teamproject.exception;

public class NestedCommentNotFoundException extends RuntimeException{
    public NestedCommentNotFoundException() {
        super();
    }

    public NestedCommentNotFoundException(String message) {
        super(message);
    }

    public NestedCommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NestedCommentNotFoundException(Throwable cause) {
        super(cause);
    }
}
