package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.validation.SimpleFieldError;
import com.example.umc3_teamproject.domain.LoginType;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.Tier;
import com.example.umc3_teamproject.dto.SignupReq;
import com.example.umc3_teamproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;


    @Transactional
    public Long saveMember(SignupReq signupReq) {
        checkDuplicateEmail(signupReq.getEmail());
        Member member = Member.builder()
                .email(signupReq.getEmail())
                .pw(signupReq.getPw())
                .nickName(signupReq.getNickName())
                .tier(Tier.BRONZE)
                .imageUrl(signupReq.getImageUrl())
                .loginType(LoginType.DEFAULT)
                .build();
        memberRepository.save(member);
    };

    private void checkDuplicateEmail(String email) {

    }




}
