package com.example.umc3_teamproject.domain.dto.response;
import com.example.umc3_teamproject.domain.item.Paragraph;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.MemberRepository;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParagraphResponseDto {
    @Autowired
    MemberRepository memberRepository;
    @Getter
    @Builder
    private static class Body {

        private String result;


        private Long memberId;
        private Long scriptId;
        private Long paragraphId;


        private String title;
        private String contents;
        //private List<String> paragraphImage_url;

        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
    }
    public ResponseEntity<?> success(Paragraph paragraph) {

        Body body = Body.builder()
                .result("success")
                .memberId(paragraph.getMemberId().getId())
                .scriptId(paragraph.getScriptId().getScriptId())
                .paragraphId(paragraph.getParagraphId())
                .title(paragraph.getTitle())
                .contents("no contents")
                .createdDate(paragraph.getCreatedDate())
                .modifiedDate(paragraph.getModifiedDate())
                .build();
        return ResponseEntity.ok(body);
    }


}
