package com.example.umc3_teamproject.User.controller;

import com.example.umc3_teamproject.User.repository.Dto.SessionUser;
import com.example.umc3_teamproject.User.service.OAuthService;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
    @ResponseBody
    @GetMapping("/kakao")
    public SessionUser kakaoCallback(@RequestParam String code) throws Exception {
        String kakaoAccessToken = oAuthService.getKakaoAccessToken(code);
        return oAuthService.createKakaoUser(kakaoAccessToken);
    }
}
