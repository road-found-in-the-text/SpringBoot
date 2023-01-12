package com.example.umc3_teamproject.exception;

public class ScriptNotFoundException extends RuntimeException{
    public ScriptNotFoundException() {
        super();
    }

    public ScriptNotFoundException(String message) {
        super(message);
    }

    public ScriptNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptNotFoundException(Throwable cause) {
        super(cause);
    }
}
