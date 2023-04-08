package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.dto.SignupReq;
import com.example.umc3_teamproject.dto.SignupRes;
import com.example.umc3_teamproject.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.Optional;

import static com.example.umc3_teamproject.domain.Tier.BRONZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    LoginService loginService;

    @Mock
    JwtService jwtService;

    @InjectMocks
    MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void 멤버생성테스트() throws ResponseException {
        // given
        SignupReq signupReq = new SignupReq();
        signupReq.setEmail("test@test.com");
        signupReq.setPw("password");
        signupReq.setNickName("test");
        signupReq.setImageUrl("http://test.com/image.jpg");

        // when
        SignupRes signupRes = memberService.createUser(signupReq);

        // then
        //확인할 만한 데이터 양식이 굉장히 불편하게 짜여져 있다. 방안 !!
        Assertions.assertEquals(1L, signupRes.getMemberId().longValue());
    }


}
