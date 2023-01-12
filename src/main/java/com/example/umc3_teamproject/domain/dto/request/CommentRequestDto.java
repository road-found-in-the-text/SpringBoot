package com.example.umc3_teamproject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommentRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createCommentRequest {
        private Long user_id;
        String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateCommentRequest {
        private String content;
    }

}
