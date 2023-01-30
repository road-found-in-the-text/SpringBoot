package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.Comment;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentResponseDto {
    @Data
    @NoArgsConstructor
    public static class Body {
        private Long forumId;
        private Long commentId;
        private Long writerId;
        private String writer;
        private String content;
        private int likeNum;
        private List<NestedCommentResponseDto.Body> nestedCommentDatas;

        public Body(Comment comment,List<NestedCommentResponseDto.Body> nestedCommentDatas) {
            this.forumId = comment.getForum().getId();
            this.commentId = comment.getId();
            this.writerId = comment.getMember().getId();
            this.writer = comment.getMember().getNickName();
            this.content = comment.getContent();
            this.likeNum = comment.getLike_num();
            this.nestedCommentDatas = nestedCommentDatas;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeResponseDto {
        @ApiParam(value = "해당 댓글 id", required = true, example = "1")
        private Long comment_id;

        @ApiParam(value = "해당 댓글 좋아요 수", required = true, example = "100")
        private int like_num;
    }

}
