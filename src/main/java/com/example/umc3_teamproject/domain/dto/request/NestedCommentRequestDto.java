package com.example.umc3_teamproject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class NestedCommentRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createNestedCommentRequest {
        // comment로 조회하는데 comment는 path parameter로 가져올 것이다.
        @NotNull(message = "대댓글 작성자의 user id를 넣어주세요. 비어있으면 안됩니다.")
        private Long user_id;
        @NotNull(message = "대댓글의 내용을 넣어주세요 비어있으면 안됩니다.")
        String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateNestedCommentRequest {
        @NotNull(message = "대댓글 수정 이후의 최종 대댓글 내용을 넣어주세요. 비어있으면 안됩니다.")
        private String content;
    }
}
