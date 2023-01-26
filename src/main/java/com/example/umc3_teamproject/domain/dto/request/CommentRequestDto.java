package com.example.umc3_teamproject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class CommentRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createCommentRequest {
        @NotNull(message = "해당 댓글을 작성한 사용자의 user id를 넣어주세요. 비어있으면 안됩니다.")
        private Long user_id;
        @NotNull(message = "댓글 내용을 넣어주세요. 비어있으면 안됩니다.")
        String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateCommentRequest {
        @NotNull(message = "수정 이후의 댓글 내용을 넣어주세요. 비어있으면 안됩니다.")
        private String content;
    }

}
