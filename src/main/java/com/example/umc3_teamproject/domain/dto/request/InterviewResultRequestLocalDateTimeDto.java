package com.example.umc3_teamproject.domain.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InterviewResultRequestLocalDateTimeDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createInterviewResult {
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime startTime;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime endTime;

        @ApiModelProperty(value = "녹음 파일", required = false)
        private MultipartFile voiceFile;

        private String voiceContent;

    }
}
