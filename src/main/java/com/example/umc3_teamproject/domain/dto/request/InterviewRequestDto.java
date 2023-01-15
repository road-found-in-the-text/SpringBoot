package com.example.umc3_teamproject.domain.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class InterviewRequestDto {

    @Getter
    @Setter
    public static class Register {

        @NotNull(message = "user id는 필수 입력값입니다.")
        private Long userId;

        @NotNull(message = "제목은 필수 입력값입니다.")
        private String title;

        // @NotNull(message = "type은 필수 입력값입니다.")
        private String type;

        @NotNull(message = "인터뷰 날짜는 필수 입력값입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate interviewDate;
    }

    @Getter
    @Setter
    public static class Update {

        @NotNull(message = "title은 필수 입력값입니다.")
        private String title;

        // @NotNull(message = "type은 필수 입력값입니다.")
        private String type;

        @NotNull(message = "인터뷰 날짜는 필수 입력값입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate interviewDate;
    }

}

