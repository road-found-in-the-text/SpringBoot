package com.example.umc3_teamproject.domain;


public enum Tier {
    BRONZE(0),
    SILVER(1),
    GOLD(2),
    PLATINUM(3),
    DIAMOND(4);

    public final int value;

    Tier(final int value){
        this.value = value;
    }
}
