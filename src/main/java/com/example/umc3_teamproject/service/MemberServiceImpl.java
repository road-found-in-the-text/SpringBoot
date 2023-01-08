package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    public void validateDuplicateUser(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getNickName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        }
    }

    @Transactional
    public Long join(Member member) {
        validateDuplicateUser(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id);
        member.setNickName(name);
    }
}
