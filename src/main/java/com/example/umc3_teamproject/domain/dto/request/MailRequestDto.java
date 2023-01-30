package com.example.umc3_teamproject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MailRequestDto {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MailDto {
        private String address;
        private String title;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class emailConfirmDto {
        private String email;
    }


}
