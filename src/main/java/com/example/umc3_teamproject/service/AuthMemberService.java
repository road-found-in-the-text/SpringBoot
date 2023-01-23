package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.domain.item.LoginType;
import com.example.umc3_teamproject.domain.item.Member;
import com.example.umc3_teamproject.dto.*;
import com.example.umc3_teamproject.repository.RefreshTokenRepository;
import com.example.umc3_teamproject.repository.SocialMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthMemberService {
    private final SocialMemberRepository socialMemberRepository;
    private final TokenService tokenService;
    private final KakaoService clientKakao;
    private final AppleService clientApple;
    private final RefreshTokenRepository refreshTokenRepository;
    private RefreshToken refreshToken;

    public AuthRes signUpOrLogIn(AuthReq authRequest) throws NoSuchAlgorithmException {
        Member member;
        if (authRequest.getLoginType() == LoginType.APPLE) {
            member = getAppleProfile(authRequest.getAccessToken());
        } else {
            member = getKakaoProfile(authRequest.getAccessToken());
        }

        Token token = tokenService.generateToken(member.getSocialId(), "USER");

        boolean isNewMember = false;
        boolean isUserSettingDone = false;

        System.out.println(member.getSocialId());

        //최초 회원가입 시
        if (socialMemberRepository.findBySocialId(member.getSocialId()).equals(Optional.empty())) {

            //member.setPoint(20);  따로 설정해줄 사항
            System.out.println(member.getId());
            System.out.println(member.getSocialId());

            socialMemberRepository.save(member);

            System.out.println(member.getSocialId());
            refreshToken = RefreshToken.builder()
                    .id(member.getSocialId())
                    .refreshToken(token.getRefreshToken())
                    .build();
            refreshTokenRepository.save(refreshToken);
            isNewMember = true;
        } else {
            Optional<Member> currentUser = socialMemberRepository.findBySocialId(member.getSocialId());
            Optional<RefreshToken> oldRefreshToken = refreshTokenRepository.findById(member.getSocialId());
            if (!oldRefreshToken.equals(Optional.empty())) {
                refreshToken = refreshTokenRepository.getById(member.getSocialId());
                refreshToken = refreshToken.updateValue(token.getRefreshToken());
            } else {
                refreshToken = RefreshToken.builder()
                        .id(member.getSocialId())
                        .refreshToken(token.getRefreshToken())
                        .build();
            }
            refreshTokenRepository.save(refreshToken);

            Member thisUser = currentUser.get();
            if(thisUser.getNickName() != null){
                isUserSettingDone = true;
            }
        }

        return AuthRes.builder()
                .isNewMember(isNewMember)
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .userSettingDone(isUserSettingDone)
                .build();
    }

    public Member getKakaoProfile(String oauthToken) {
        Member kakaoUser = clientKakao.getMemberData(oauthToken);
        System.out.println("getKakao");
        return kakaoUser;
    }

    public Member getAppleProfile(String oauthToken) throws NoSuchAlgorithmException {
        Member appleUser = clientApple.getMemberData(oauthToken);
        System.out.println("getApple");
        return appleUser;
    }

    public TokenReissue reissue(TokenReissue tokenReissueRequest) {
        Long timeLeft = tokenService.verifyRefreshToken(tokenReissueRequest.getRefreshToken());
        if (timeLeft == 0) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
        String currentSocialId = tokenService.getSocialId(tokenReissueRequest.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findById(currentSocialId)
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        if (!refreshToken.getToken().equals(tokenReissueRequest.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        Token token = tokenService.generateToken(currentSocialId, "USER");
        TokenReissue tokenReissueResponse;
        if (timeLeft < 1000L * 60L * 60L * 24L * 3L) {

            refreshToken = refreshToken.updateValue(token.getRefreshToken());
            refreshTokenRepository.save(refreshToken);

            tokenReissueResponse = TokenReissue.builder()
                    .accessToken(token.getAccessToken())
                    .refreshToken(token.getRefreshToken())
                    .build();
        } else {
            String accessToken = tokenService.generateAccessToken(currentSocialId, "USER");
            tokenReissueResponse = TokenReissue.builder()
                    .accessToken(token.getAccessToken())
                    .refreshToken(tokenReissueRequest.getRefreshToken())
                    .build();
        }
        return tokenReissueResponse;
    }
//
//    public User getKakaoProfile(String oauthToken) {
//        User kakaoUser = clientKakao.getUserData(oauthToken);
//        System.out.println("getKakao");
//        return kakaoUser;
//    }

    public void logout(Member member) throws ResponseException {
        RefreshToken refreshToken = refreshTokenRepository
                .findById(member.getSocialId()).orElseThrow(()-> new ResponseException(USER_NOT_FOUND));

        refreshToken.setToken(null);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void withdrawl(Member member){
        refreshTokenRepository.deleteById(member.getSocialId());
        member.setSocialId(null);
        socialMemberRepository.save(member);
    }
}
