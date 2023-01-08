package com.example.umc3_teamproject.exception;

public class NoScriptId extends RuntimeException{
    public NoScriptId() {
        super();
    }

    public NoScriptId(String message) {
        super(message);
    }

    public NoScriptId(String message, Throwable cause) {
        super(message, cause);
    }

    public NoScriptId(Throwable cause) {
        super(cause);
    }
}
