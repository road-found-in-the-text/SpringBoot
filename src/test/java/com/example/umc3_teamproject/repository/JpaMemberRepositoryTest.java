package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.assertNotNull;

@DataJpaTest
public class JpaMemberRepositoryTest {

    @Autowired
    private JpaMemberRepository jpaMemberRepository;

    @Test
    public void JpaMemberRepository가Null이아님() {
        Assertions.assertThat(jpaMemberRepository).isNotNull();
    }

//    @Test
//    void member등록() {
//        Member member = Member.builder()
//
//                .build();
//    }
}
