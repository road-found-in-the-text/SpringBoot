package com.example.umc3_teamproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createCommentResponse {
    private Long comment_id;
    private Long forum_id;
    private String user_nickname;
    private String cotent;
}
