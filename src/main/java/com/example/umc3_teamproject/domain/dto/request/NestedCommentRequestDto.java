package com.example.umc3_teamproject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class NestedCommentRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createNestedCommentRequest {
        // comment로 조회하는데 comment는 path parameter로 가져올 것이다.
        private Long user_id;
        String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateNestedCommentRequest {
        private String content;
    }
}
