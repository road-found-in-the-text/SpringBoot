package com.example.umc3_teamproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIntroReq {

    private Long memberId;

    @Size(min = 1, max = 30, message = "한줄소개는 1글자 이상 150자 이내로 작성 가능합니다.")
    private String introduction;
}
