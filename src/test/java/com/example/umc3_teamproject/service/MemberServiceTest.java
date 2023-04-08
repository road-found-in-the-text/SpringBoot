package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.dto.SignupReq;
import com.example.umc3_teamproject.dto.SignupRes;
import com.example.umc3_teamproject.repository.MemberRepository;
import com.example.umc3_teamproject.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MemberServiceTest {
    private final String email = "test@gmail.com";
    private final String name = "test";
    private final String pw = "test1234!!";


    @Test
    public void 멤버서비스생성테스트_mock사용() {
        MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
        LoginService loginService = Mockito.mock(LoginService.class);
        JwtService jwtService = Mockito.mock(JwtService.class);

        MemberService memberService = new MemberService(memberRepository,loginService, jwtService);

        Assertions.assertNotNull(memberService);
    }
}
