package com.example.umc3_teamproject.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoRes {

    private Long id;
    private KakaoAccount kakaoAccount;

    @Getter @NoArgsConstructor @AllArgsConstructor
    public static class KakaoAccount {
        private String email;
        private String nickName;
    }

}
