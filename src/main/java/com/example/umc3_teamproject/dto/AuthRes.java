package com.example.umc3_teamproject.dto;


import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(value = "인증 후 응답")
public class AuthRes {
    private String accessToken;
    private String refreshToken;
    private Boolean isNewMember;
    private Boolean userSettingDone;

}
