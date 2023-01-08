package com.example.umc3_teamproject.domain.dto.request;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

public class ParagraphRequestDto {
    @Getter
    @Setter
    public static class Register {

        @NotNull(message = "user id는 필수 입력값입니다.")
        private Long userId;
        @NotNull(message = "script id는 필수 입력값입니다.")
        private Long scriptId;
        @NotNull(message = "title은 필수 입력값입니다.")
        private String title;
        private String type;
        private String imageUrl;
    }
    @Getter
    @Setter
    public static class Update {
        @NotNull(message = "title은 필수 입력값입니다.")
        private String title;
        private String type;
        private Long scriptId;
    }
}
