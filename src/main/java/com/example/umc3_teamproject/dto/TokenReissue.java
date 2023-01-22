package com.example.umc3_teamproject.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel(value = "토큰")
public class TokenReissue {

    private String accessToken;
    private String refreshToken;
}
