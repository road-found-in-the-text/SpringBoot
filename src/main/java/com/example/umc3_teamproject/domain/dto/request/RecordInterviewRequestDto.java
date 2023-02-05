package com.example.umc3_teamproject.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class RecordInterviewRequestDto {
    @Getter
    @Setter
    public static class Register {

        @NotNull(message = "user id는 필수 입력값입니다.")
        private Long memberId;

        @NotNull(message = "record는 필수 입력값입니다.")
        private Float record;

        @NotNull(message = "interview id는 필수 입력값입니다.")
        private Long interviewId;


    }

    @Getter
    @Setter
    public static class Update {

        @NotNull(message = "record는 필수 입력값입니다.")
        private Float record;


    }
}
