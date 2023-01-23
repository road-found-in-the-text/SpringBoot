package com.example.umc3_teamproject.repository;
import com.example.umc3_teamproject.domain.item.Member;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired 
    MemberRepository memberRepository;



    @Test
    public void testFindUserIdByEmail() throws Exception {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@gmail.com");
        member.setPw("test1234");
        member.setNickName("test");
        member.setTier(0);


        String user_main="test@gmail.com";

        Long id= memberRepository.findUserIdByEmail(user_main);
        System.out.println(id);

    }
}