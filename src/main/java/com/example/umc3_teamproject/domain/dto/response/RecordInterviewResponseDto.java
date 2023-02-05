package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.RecordInterview;
import com.example.umc3_teamproject.repository.MemberRepository;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RecordInterviewResponseDto {
    @Autowired
    MemberRepository memberRepository;

    @Getter
    @Builder
    private static class Body {

        private String result;

        private Long memberId;
        private Long interviewId;

        private Float record;

    }

    public ResponseEntity<?> success(RecordInterview record_interview) {

        RecordInterviewResponseDto.Body body = RecordInterviewResponseDto.Body.builder()
                .result("success")
                .memberId(record_interview.getMemberId().getId())
                .interviewId(record_interview.getInterviewId().getInterviewId())
                .record(record_interview.getRecord())
                .build();
        return ResponseEntity.ok(body);
    }

}
