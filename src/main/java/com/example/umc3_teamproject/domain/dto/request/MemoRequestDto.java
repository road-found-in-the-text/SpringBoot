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
        @NotNull(message = "script 또는 interview 둘 중에 하나 기입하시면 됩니다.(해당 메모가 script인지 interview인지 구분하기 위함.")
        private String type;

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
