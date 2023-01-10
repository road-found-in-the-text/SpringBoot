package com.example.umc3_teamproject.domain;

public enum LoginType {
    DEFAULT(0), OAUTH(1);

    public final int value;

    LoginType(final int value){
        this.value = value;
    }
}
