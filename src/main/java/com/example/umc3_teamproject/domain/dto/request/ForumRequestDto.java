package com.example.umc3_teamproject.domain.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ForumRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createForumRequest {
        private String title;
        private String content;
        private List<ScriptIdsToRequest> scriptIds;

        @ApiModelProperty(value = "이미지 파일", required = false)
        private List<MultipartFile> imageFiles = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScriptIdsToRequest {
        private Long script_id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateForumRequest {
        private String title;
        private String content;
        private List<ScriptIdsToRequest> scriptIds;

        @ApiModelProperty(value = "삭제한 이미지 경로를 제외한 남아있는 게시글 이미지 경로")
        private List<String> saveImageUrl = new ArrayList<>();

        @ApiModelProperty(value = "이미지 파일", required = false)
        private List<MultipartFile> imageFiles = new ArrayList<>();
    }
}
