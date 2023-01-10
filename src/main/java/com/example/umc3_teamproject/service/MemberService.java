package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.Member;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public interface MemberService {
    //회원가입
    @Transactional
    public Long saveMember(Member member);

    UserDetails loginOAuth2Member(String provider, OAuth2Token oAuth2Token, OAuth2UserInfo userinfo);

    Optional<OAuth2AccountDTO> getOAuth2Account(String nickName);

    //회원 전체 조회
    public List<Member> findMembers();

    //회원 한 명 조희
    public Member findById(Long memberId);

    @Transactional
    public void update(Long id, String name);
}
