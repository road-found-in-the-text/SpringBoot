package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.Comment;
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
        private Long userId;
        private String userNickname;
        private String content;
        private int like_num;
        private List<NestedCommentResponseDto.Body> nestedCommentDatas;

        public Body(Comment comment,List<NestedCommentResponseDto.Body> nestedCommentDatas) {
            this.forumId = comment.getForum().getId();
            this.commentId = comment.getId();
            this.userId = comment.getMember().getId();
            this.userNickname = comment.getMember().getNickName();
            this.content = comment.getContent();
            this.like_num = comment.getLike_num();
            this.nestedCommentDatas = nestedCommentDatas;
        }
    }

}