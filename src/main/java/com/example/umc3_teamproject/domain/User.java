package com.example.umc3_teamproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    private Long userId;
    private String userName;
    private String userPw;


    public Long getId() {
    }

    public void setUsername(String memberA) {
    }
}
