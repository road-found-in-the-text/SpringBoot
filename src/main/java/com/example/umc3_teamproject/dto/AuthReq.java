package com.example.umc3_teamproject.dto;

import com.example.umc3_teamproject.domain.item.LoginType;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter @NoArgsConstructor
@ApiModel(value = "인증 요청")
public class AuthReq {

    private String accessToken;
    private LoginType loginType;
}
