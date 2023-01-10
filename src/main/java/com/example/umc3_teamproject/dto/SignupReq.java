package com.example.umc3_teamproject.dto;


import com.example.umc3_teamproject.domain.Tier;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //null일 경우 생성되지 않는다.
public class SignupReq {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message="이메일 양식을 확인해주세요")
    private String email;

    @NotBlank(message="비밀번호를 입력해주세요")
    @Pattern(regexp = "[a-zA-Z!@#$%^&*-_]{6,20}",message="6~20길이의 알파벳과 숫자로 입력해주세요")
    private String pw;


    private Tier tier;

    @Pattern(regexp="[가-힣|a-z|A-Z|0-9]{1,30}",message="1~30길이의 알파벳, 한글, 숫자로 입력해주세요")
    @NotBlank(message="닉네임을 입력해주세요.")
    private String nickName;

    private String imageUrl;

    public SignupReq(String email, String pw, Tier tier, String nickName, String imageUrl){
        this.email=email;
        this.pw = pw;
        this.nickName = nickName;
        this.tier = tier;
        this.imageUrl = imageUrl;
    }

}
