package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.User;
import com.example.umc3_teamproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long save(User user){
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public User findOne(Long id){
        return userRepository.findOne(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
