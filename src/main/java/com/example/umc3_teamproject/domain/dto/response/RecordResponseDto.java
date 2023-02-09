package com.example.umc3_teamproject.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class RecordResponseDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Body {

        // sciprt와 intervie 구분
        private int result_count;
        private double mean;
        private double score1;
        private double score2;
        private double score3;
        private double score4;
        private double score5;
    }
}
