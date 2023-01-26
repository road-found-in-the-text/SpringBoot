package com.example.umc3_teamproject.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class InterviewResultRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createInterviewResult {
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @NotNull(message = "녹음 시작 시간을 String 형식으로 기입해주세요. 비어있으면 안됩니다.")
        private String startTime;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @NotNull(message = "녹음 끝난 시간을 String 형식으로 기입해주세요. 비어있으면 안됩니다.")
        private String endTime;

        @ApiModelProperty(value = "녹음 파일", required = false)
        @NotNull(message = "녹음 파일을 넣어주세요. 비어있으면 안됩니다.")
        private MultipartFile voiceFile;

        @NotNull(message = "녹음 파일을 Text로 변환한 값을 넣어주세요. 비어있으면 안됩니다.")
        private String voiceContent;

    }
}
