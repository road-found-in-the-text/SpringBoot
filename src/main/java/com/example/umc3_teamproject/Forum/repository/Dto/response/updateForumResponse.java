package com.example.umc3_teamproject.Forum.repository.Dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class updateForumResponse<T> {
    private T data;
}