package com.example.umc3_teamproject.domain.item;

public enum VoiceSpeed {
    slow("느려요"),
    proper("적당해요"),
    fast("빨라요");

    public final String speed;

    VoiceSpeed(final String speed){
        this.speed = speed;
    }
}
