package com.example.umc3_teamproject.exception;

public class NoForumExist extends RuntimeException{
    public NoForumExist() {
        super();
    }

    public NoForumExist(String message) {
        super(message);
    }

    public NoForumExist(String message, Throwable cause) {
        super(message, cause);
    }

    public NoForumExist(Throwable cause) {
        super(cause);
    }
}
