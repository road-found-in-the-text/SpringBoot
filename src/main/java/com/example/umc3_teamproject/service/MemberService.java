package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface MemberService {


    //회원가입
    @Transactional
    public Long join(Member member);

    void validateDuplicateUser(Member member);

    //회원 전체 조회
    public List<Member> findMembers();

    //회원 한 명 조희
    public Member findById(Long memberId);

    @Transactional
    public void update(Long id, String name);
}
