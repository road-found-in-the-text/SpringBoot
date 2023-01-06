package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.user.User;
import com.example.umc3_teamproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //회원가입
    public Long join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        //EXCEPTION

    }

    //회원 전체 조회


}
