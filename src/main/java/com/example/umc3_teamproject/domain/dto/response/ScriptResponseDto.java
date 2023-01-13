package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.Script;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScriptResponseDto {

    @Getter
    @Builder
    private static class Body {

        private String result;

        private Long userId;
        private Long scriptId;

        private String title;
        private String type;

        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
    }

    public ResponseEntity<?> success(Script script) {
        Body body = Body.builder()
                .result("success")
                .userId(script.getUserId())
                .scriptId(script.getScriptId())
                .title(script.getTitle())
                .type(script.getType())
                .createdDate(script.getCreatedDate())
                .modifiedDate(script.getModifiedDate())
                .build();
        return ResponseEntity.ok(body);
    }
}