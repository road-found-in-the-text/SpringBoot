package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.NestedComment;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NestedCommentResponseDto {
    @Data
    @NoArgsConstructor
    public static class Body {
        private Long commentId;
        private Long nestedCommentId;
        private Long writerId;
        private String writer;
        private String content;
        private int likeNum;

        public Body(NestedComment nestedComment) {
            this.commentId = nestedComment.getComment().getId();
            this.nestedCommentId = nestedComment.getId();
            this.writerId = nestedComment.getMember().getId();
            this.writer = nestedComment.getMember().getNickName();
            this.content = nestedComment.getContent();
            this.likeNum = nestedComment.getLike_num();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeResponseDto {
        @ApiParam(value = "해당 대댓글 id", required = true, example = "1")
        private Long nested_comment_id;

        @ApiParam(value = "해당 대댓글의 좋아요 수", required = true, example = "100")
        private int like_num;
    }
}
