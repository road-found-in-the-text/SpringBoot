package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.domain.item.Member;
import com.example.umc3_teamproject.dto.AuthReq;
import com.example.umc3_teamproject.dto.AuthRes;
import com.example.umc3_teamproject.dto.TokenReissue;
import com.example.umc3_teamproject.service.AuthMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"인증 관련 API"})
public class AuthController {

    private final AuthMemberService authMemberService;

    @ApiOperation(value = "로그인 및 회원가입 api")
    @PostMapping("/login")
    public ResponseEntity<AuthRes> getTokens(@RequestBody AuthReq authRequest) throws NoSuchAlgorithmException {
        AuthRes authResponse = authMemberService.signUpOrLogIn(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @ApiOperation(value= "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<TokenReissue> reissue(@RequestBody TokenReissue tokenReissueRequest) {
        TokenReissue tokenReissueResponse = authMemberService.reissue(tokenReissueRequest);
        return ResponseEntity.ok(tokenReissueResponse);
    }

    @ApiOperation(value = "로그아웃")
    @PatchMapping("/logout")
    public ResponseEntity<Void> logOut ( Member member) throws ResponseException {
        authMemberService.logout(member);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "회원탈퇴")
    @PatchMapping("/withdrawl")
    public ResponseEntity<Void> withdrawl ( Member member) {
        authMemberService.withdrawl(member);
        return ResponseEntity.ok().build();
    }

}
