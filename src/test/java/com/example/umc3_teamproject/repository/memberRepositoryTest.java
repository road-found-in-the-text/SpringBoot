package com.example.umc3_teamproject.repository;
import com.example.umc3_teamproject.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class memberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    public void testUser() throws Exception {
        /*
        //given
        Member member = new Member();
        member.setNickName("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getNickName()).isEqualTo(member.getNickName());
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member:" + (findMember == member));

         */
    }
}