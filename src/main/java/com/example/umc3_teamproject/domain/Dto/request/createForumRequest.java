package com.example.umc3_teamproject.Forum.repository.Dto.request;

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
public class createForumRequest {
    private String title;
    private String content;
    private List<ScriptIdsToRequest> scriptIdToRequests;

    @ApiModelProperty(value = "이미지 파일", required = false)
    private List<MultipartFile> imageFiles = new ArrayList<>();
}
