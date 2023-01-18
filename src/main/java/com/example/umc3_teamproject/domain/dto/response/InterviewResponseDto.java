package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.Interview;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Component
public class InterviewResponseDto {

    @Getter
    @Builder
    private static class Body {

        private String result;

        private Long userId;
        private Long interviewId;

        private String title;
        private String type;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate interviewDate;

        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
    }

    public ResponseEntity<?> success(Interview interview) {
        InterviewResponseDto.Body body = InterviewResponseDto.Body.builder()
                .result("success")
                .userId(interview.getUserId())
                .interviewId(interview.getInterviewId())
                .title(interview.getTitle())
                .type(interview.getType())
                .interviewDate(interview.getInterviewDate())
                .createdDate(interview.getCreatedDate())
                .modifiedDate(interview.getModifiedDate())
                .build();
        return ResponseEntity.ok(body);
    }
}
