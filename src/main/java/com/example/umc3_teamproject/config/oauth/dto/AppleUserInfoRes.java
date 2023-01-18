package com.example.umc3_teamproject.config.oauth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AppleUserInfoRes {


   private String id;
    private String iss; //issuer 토큰 발급자
    private String sub; //subject 토큰 주제
    private String aud; //audience 토큰 수신자
    private String nbf; //not before 정의된 시간 이후 토큰 활성화
    private String exp; //expiration 만료시간
    private String iat; //issued at 토큰 발급 시간
    private String jti; //jwt id 토큰 식별자
}