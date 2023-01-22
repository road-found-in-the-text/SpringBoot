package com.example.umc3_teamproject.domain.item;

public enum LoginType {
    DEFAULT(0), APPLE(1), KAKAO(2);

    public final int value;

    LoginType(final int value){
        this.value = value;
    }
}
