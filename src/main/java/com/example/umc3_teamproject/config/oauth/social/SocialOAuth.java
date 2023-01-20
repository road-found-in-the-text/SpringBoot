package com.example.umc3_teamproject.config.oauth.social;


import com.example.umc3_teamproject.domain.LoginType;

public interface SocialOAuth {

    String getRedirectUri();
    String getAccessToken(String authorizationCode);

    Object getUserInfo(String accessToken);

    default LoginType type() {
        if (this instanceof AppleOAuth) {
            return LoginType.APPLE;
        } else
        if (this instanceof KakaoOAuth) {
            return LoginType.KAKAO;
        } else {
            return null;
        }
    }
}