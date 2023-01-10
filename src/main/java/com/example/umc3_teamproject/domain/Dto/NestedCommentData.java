package com.example.umc3_teamproject.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NestedCommentData {
    private Long nested_comment_id;
    private String writer;
    private String content;
    private int like_num;
}
