package com.example.umc3_teamproject.dto;

import com.example.umc3_teamproject.domain.LoginType;
import com.example.umc3_teamproject.domain.Tier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberRes {

    private Long id;
    private String nickName;
    private String imageUrl;
    private int tier;
    private int loginType;

    //허용
    private boolean memberStatus;

    private boolean blockStatus;

}
