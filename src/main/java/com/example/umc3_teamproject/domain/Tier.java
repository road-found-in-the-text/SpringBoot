package com.example.umc3_teamproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


public enum Tier {
    BRONZE(0),
    SILVER(1),
    GOLD(2),
    PLATINUM(3),
    DIAMOND(4);

    private int type;

    Tier(int type) {
        this.type = type;
    }

    public int getTier() {
        return type;
    }
}
