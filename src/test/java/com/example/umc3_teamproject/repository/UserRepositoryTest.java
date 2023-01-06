package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    @Transactional
    public void testUser() throws Exception {

        //given
        User user = new User();
        user.setUsername("memberA");

        //when
        Long savedId = userRepository.save(user);
        User findUser = userRepository.find(savedId);

        //then
        Assertions.assertThat(findUser.getId()).isEqualTo(user.getId());
        Assertions.assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(findUser).isEqualTo(user);
        System.out.println("findUser == user:" + (findUser == user));
    }
}