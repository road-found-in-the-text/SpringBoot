package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.Paragraph;
import com.example.umc3_teamproject.domain.item.Script;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InterviewParagraphResponseDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {
        private Long interviewId;
        private Long interviewParagraphId;
        private String title;
        private String contents;
        private int paragraph_order;

        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
    }
}
