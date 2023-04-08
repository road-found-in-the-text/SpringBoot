package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Member;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JpaMemberRepositoryTest {

    //Jpa에서는 mock 이 불가능. 좀 더 다른 방법을 생각한다. @DataJPA라던가..? Autowired
    @Mock
    JpaMemberRepository jpaMemberRepository;

    @Test
    void 이메일로찾기성공() {
        // given
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setPw("test1234!!");
        jpaMemberRepository.save(member);
        System.out.println(member.getId());
        // when
        Optional<Member> result = jpaMemberRepository.findByEmail("test@test.com");

        // then
        assertTrue(result.isPresent());
        assertEquals(member.getEmail(), result.get().getEmail());
    }

    @Test
    void 이메일이있는지확인() {
        // given
        Member member = new Member();
        member.setEmail("test@test.com");
        jpaMemberRepository.save(member);

        // when
        boolean result = jpaMemberRepository.existsMemberByEmail("test@test.com");

        // then
        assertTrue(result);
    }
}
