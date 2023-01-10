package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.dto.NestedCommentData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDataToGetResult {
    private Long userId;
    private Long forumId;
    private Long commentId;
    private String content;
    private int like_num;
    private List<NestedCommentData> nestedCommentDatas;

}