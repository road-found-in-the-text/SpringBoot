package com.example.umc3_teamproject.dto;

import com.example.umc3_teamproject.domain.LoginType;
import com.example.umc3_teamproject.domain.Tier;

public class MemberRes {

    private Long id;
    private String nickName;
    private String imageUrl;
    private Tier tier;
    private LoginType loginType;

    //허용
    private boolean comments_alarm_permission;
    private boolean voice_permission;
    private boolean event_permission;
    private boolean report_status;

}
