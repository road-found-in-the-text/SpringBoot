package com.example.umc3_teamproject.domain.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createCommentRequest {
    private Long user_id;
    String content;
}