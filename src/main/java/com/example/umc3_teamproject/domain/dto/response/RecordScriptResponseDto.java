package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.RecordScript;
import com.example.umc3_teamproject.repository.MemberRepository;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class RecordScriptResponseDto {
    @Autowired
    MemberRepository memberRepository;

    @Getter
    @Builder
    private static class Body {

        private String result;

        private Long memberId;
        private Long scriptId;

        private Float record;

    }

    public ResponseEntity<?> success(RecordScript record_script) {

        Body body = Body.builder()
                .result("success")
                .memberId(record_script.getMemberId().getId())
                .scriptId(record_script.getScriptId().getScriptId())
                .record(record_script.getRecord())
                .build();
        return ResponseEntity.ok(body);
    }


}