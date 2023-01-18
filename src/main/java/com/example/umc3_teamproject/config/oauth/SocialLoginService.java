package com.example.umc3_teamproject.config.oauth;


import com.example.umc3_teamproject.config.oauth.social.SocialOAuth;
import com.example.umc3_teamproject.domain.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SocialLoginService<T> {


    private final List<SocialOAuth> socialOAuthList;
    private final HttpServletResponse httpServletResponse;

    public void getRedirectUri(LoginType socialLoginType) {
        SocialOAuth socialOAuth = this.findSocialOAuthByType(socialLoginType);
        String redirectUri = socialOAuth.getRedirectUri();
        try {
            httpServletResponse.sendRedirect(redirectUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAccessToken(LoginType socialLoginType, String authorizationCode) {
        SocialOAuth socialOAuth = this.findSocialOAuthByType(socialLoginType);
        return socialOAuth.getAccessToken(authorizationCode);
    }

    public Object getUserInfo(LoginType socialLoginType, String accessToken) {
        SocialOAuth socialOAuth = this.findSocialOAuthByType(socialLoginType);
        return socialOAuth.getUserInfo(accessToken);
    }

    private SocialOAuth findSocialOAuthByType(LoginType socialLoginType) {
        return socialOAuthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}