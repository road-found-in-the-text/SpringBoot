package com.example.umc3_teamproject.domain.Dto.response;

import com.example.umc3_teamproject.domain.Dto.NestedCommentData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class getCommentResponse {
    private Long comment_id;
    private Long forum_id;
    private String user_nickname;
    private String cotent;
    private List<NestedCommentData> nestedCommentDatas;
}
