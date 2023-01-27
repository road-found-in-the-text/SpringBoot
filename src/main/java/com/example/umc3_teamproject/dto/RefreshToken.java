package com.example.umc3_teamproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    private String id;

    private String token;

    public RefreshToken updateValue(String token) {
        this.token = token;
        return this;
    }

    @Builder
    public RefreshToken(String id, String refreshToken) {
        this.id = id;
        this.token = refreshToken;
    }
}