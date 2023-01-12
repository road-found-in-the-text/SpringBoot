package com.example.umc3_teamproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRes {

    private Long memberId;
    private String jwt;
}
