package com.example.umc3_teamproject.domain.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class RecordRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createRecordRequest {
        private double score1;
        private double score2;
        private double score3;
        private double score4;
        private double score5;
        private int elapsed_minute;
        private int elapsed_second;
    }
}
