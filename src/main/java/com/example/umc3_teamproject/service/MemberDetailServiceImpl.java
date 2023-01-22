package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus;
import com.example.umc3_teamproject.repository.SocialMemberRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class MemberDetailServiceImpl implements UserDetailsService{
    @Autowired
    private SocialMemberRepository socialMemberRepository;

    @SneakyThrows
    @Override
    public CurrentUserDetails loadUserByUsername(String socialId) {
        return socialMemberRepository.findBySocialId(socialId)
                .map(u -> new CurrentUserDetails(u, Collections.singleton(new SimpleGrantedAuthority("USER"))))
                .orElseThrow(() -> new ResponseException(ResponseTemplateStatus.USER_NOT_FOUND));
    }



}
