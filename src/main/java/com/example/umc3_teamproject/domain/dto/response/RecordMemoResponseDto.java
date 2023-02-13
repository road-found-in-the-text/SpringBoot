package com.example.umc3_teamproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class RecordMemoResponseDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Body {
        // sciprt와 intervie 구분
        private int result_count;
        private double mean;
        private int total_elapsed_minute;
        private int total_elapsed_second;
        private List<RecordResponseDto.Body> records;
        private List<MemoResponseDto.createBody> memoList;
    }
}
