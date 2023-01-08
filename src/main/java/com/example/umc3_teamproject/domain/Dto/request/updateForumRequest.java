package com.example.umc3_teamproject.domain.Dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class updateForumRequest {
    private String title;
    private String content;
    private List<ScriptIdsToRequest> scriptIds;
    private List<String> saveImageUrl = new ArrayList<>();

    @ApiModelProperty(value = "게시글 이미지", required = false)
    private List<MultipartFile> imageFiles = new ArrayList<>();
}
