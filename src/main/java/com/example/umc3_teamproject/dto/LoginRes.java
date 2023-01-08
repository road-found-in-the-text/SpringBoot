package com.example.umc3_teamproject.dto;

import com.example.umc3_teamproject.domain.LoginType;
import com.example.umc3_teamproject.domain.Tier;

public class LoginRes {
    private Long id;
    private String nickName;
    private String imageUrl;
    private Tier tier;
    private LoginType loginType;
}
