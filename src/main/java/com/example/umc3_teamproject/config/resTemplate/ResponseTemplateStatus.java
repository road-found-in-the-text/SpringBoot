package com.example.umc3_teamproject.config.resTemplate;

import lombok.Getter;

@Getter
public enum ResponseTemplateStatus {

    //1000: 요청 성공 및 오류

    SUCCESS(true, "요청 성공", 1000),
    FAIL(false, "요청 실패", 1004),


    //2000: Request 성공 및 오류
    EMPTY_JWT(false, "JWT를 입력해주세요.", 2001),
    INVALID_JWT(false, "유효하지 않은 JWT입니다.", 2002),

    //3000: DB 부분 오류
    DATABASE_ERROR(false, "데이버베이스 요청 오류", 3000);

    private final boolean isSuccess;
    private final String message;
    private final int code;

    private ResponseTemplateStatus(boolean isSuccess, String message, int code){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
