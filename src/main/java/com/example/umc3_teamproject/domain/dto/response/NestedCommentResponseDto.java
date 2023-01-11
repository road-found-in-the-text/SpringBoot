package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.NestedComment;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NestedCommentResponseDto {
    @Data
    @NoArgsConstructor
    public static class Body {
        private Long comment_id;
        private Long nested_comment_id;
        private Long user_id;
        private String user_nickname;
        private String content;
        private int like_num;

        public Body(NestedComment nestedComment) {
            this.comment_id = nestedComment.getComment().getId();
            this.nested_comment_id = nestedComment.getId();
            this.user_id = nestedComment.getMember().getId();
            this.user_nickname = nestedComment.getMember().getNickName();
            this.content = nestedComment.getContent();
            this.like_num = nestedComment.getLike_num();
        }
    }
}
