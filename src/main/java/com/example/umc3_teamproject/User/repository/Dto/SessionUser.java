package com.example.umc3_teamproject.User.repository.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser {

    private Long id;
    private String email;


    public void createSessionUser(Long id, String email){
        this.id = id;
        this.email = email;
    }
}
