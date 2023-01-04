package com.example.umc3_teamproject.Forum.repository.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetResult<T> {
    private int count;
    private T data;
}