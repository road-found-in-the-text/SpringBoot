package com.example.umc3_teamproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReq {
    private String email;
    private String pw;

    public LoginReq(String email, String pw){
        this.email = email;
        this.pw = pw;
    }
}
