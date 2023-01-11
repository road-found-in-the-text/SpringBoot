package com.example.umc3_teamproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRes {

    private int memberId;
    private String jwt;
}
