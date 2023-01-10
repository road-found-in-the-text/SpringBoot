package com.example.umc3_teamproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter @Setter
@AllArgsConstructor
public class UpdateNickNameReq {

    private Long userIdx;

    @Size(min = 1, max = 30, message = "이름이 입력되지 않았거나 너무 긴 이름입니다.")
    private String nickName;

    @Builder
    public UpdateNickNameReq(String nickName) {
        this.nickName = nickName;
    }
}
