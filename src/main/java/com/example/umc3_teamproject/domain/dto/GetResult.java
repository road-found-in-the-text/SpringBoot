package com.example.umc3_teamproject.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetResult<T> {
    private String result = "success";
    private int count;
    private T data;

    public GetResult(int count, T data){
        this.count = count;
        this.data = data;
    }
}
