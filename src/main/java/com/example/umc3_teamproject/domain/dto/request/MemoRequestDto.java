package com.example.umc3_teamproject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class MemoRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createRequest {
        @NotNull(message = "메모 내용을 넣어주세요. 비어있으면 안됩니다.")
        private String memo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateRequest {
        @NotNull(message = "메모 내용을 넣어주세요. 비어있으면 안됩니다.")
        private String memo;
    }

}
