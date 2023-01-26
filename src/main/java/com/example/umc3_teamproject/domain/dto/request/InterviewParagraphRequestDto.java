package com.example.umc3_teamproject.domain.dto.request;

import com.example.umc3_teamproject.domain.item.Script;
import lombok.*;

import javax.validation.constraints.NotNull;

public class InterviewParagraphRequestDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Register {

        @NotNull(message = "interview id는 필수 입력값입니다.")
        private Long interview_id;
        @NotNull(message = "title은 필수 입력값입니다.")
        private String title;
        @NotNull(message = "contents는 필수 입력값입니다.")
        private String content;
        @NotNull(message = "해당 paragraph의 순서")
        private int paragraph_order;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        @NotNull(message = "수정된 이후의 최종 title을 넣어주세요 비어있으면 안됩니다.")
        private String title;
        @NotNull(message = "수정 이후의 최종 content를 넣어주세요 비어있으면 안됩니다.")
        private String content;
        @NotNull(message = "수정 이후의 최종 paragraph 순서를 넣어주세요 비어있으면 안됩니다.")
        private int paragraph_order;

    }
}