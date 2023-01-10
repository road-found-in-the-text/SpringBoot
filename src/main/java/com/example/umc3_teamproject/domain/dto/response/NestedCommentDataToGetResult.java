package com.example.umc3_teamproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NestedCommentDataToGetResult {
    private Long user_id;
    private Long comment_id;
    private Long nested_comment_id;
    private String content;
    private int like_num;
}