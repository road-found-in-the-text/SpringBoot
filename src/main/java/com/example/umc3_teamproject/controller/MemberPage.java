package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.Tier;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter @Setter
public class MemberPage {
    @NotEmpty(message="회원 이름은 필수입니다.")
    private String name;
    private Tier tier;
    private String quotes;
}
