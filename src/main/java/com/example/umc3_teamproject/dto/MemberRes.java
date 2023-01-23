package com.example.umc3_teamproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class MemberRes {

    private Long id;
    private String socialId;
    private String email;
    private String nickName;
    private String imageUrl;
    private int tier;
    private int loginType;

    //허용
    private int memberStatus;

    private int blockStatus;

}
