package com.example.umc3_teamproject.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class EmailRequestDto {
    @Getter
    @Setter
    public static class Register {

        @NotNull(message = "사용자의 email은 필수 입력값입니다.")
        private String email;

        // @NotNull(message = "title은 필수 입력값입니다.")
        // private String title;

        // @NotNull(message = "type은 필수 입력값입니다.")
        // private String type;

    }
}
