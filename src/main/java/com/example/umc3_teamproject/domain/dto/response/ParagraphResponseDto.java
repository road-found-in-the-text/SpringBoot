package com.example.umc3_teamproject.domain.dto.response;
import com.example.umc3_teamproject.domain.item.Paragraph;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ParagraphResponseDto {
    @Getter
    @Builder
    private static class Body {

        private String result;
        private Long userId;
        private Long paragraphId;
        private Long scriptId;
        private String contents;
        //private List<String> paragraphImage_url;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
    }
    public ResponseEntity<?> success(Paragraph paragraph) {
        Body body = Body.builder()
                .result("success")
                .userId(paragraph.getUserId())
                .scriptId(paragraph.getScriptId())
                .paragraphId(paragraph.getParagraphId())
                .contents(paragraph.getContents())
                .createdDate(paragraph.getCreatedDate())
                .modifiedDate(paragraph.getModifiedDate())
                .build();
        return ResponseEntity.ok(body);
    }
}
