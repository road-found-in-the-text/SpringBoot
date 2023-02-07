package com.example.umc3_teamproject.domain.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.umc3_teamproject.domain.item.Script;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ParagraphRequestDto {
    @Getter
    @Setter
    public static class Register {

        @NotNull(message = "user id는 필수 입력값입니다.")
        private Long memberId;

       @NotNull(message = "script id는 필수 입력값입니다.")
       private Long scriptId;

       @NotNull(message = "contents는 필수 입력값입니다.")
       private String contents;

        @NotNull(message = "title은 필수 입력값입니다.")
        private String title;


        //이미지 파일은 List로 여러 개의 파일을 받는다.
        //@ApiModelProperty(value = "이미지 파일", required = false)
        //private List<MultipartFile> imageFiles = new ArrayList<>();

    }
    @Getter
    @Setter
    public static class Update {
        @NotNull(message = "title은 필수 입력값입니다.")
        private String title;


    }
}