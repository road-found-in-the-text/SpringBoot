package com.example.umc3_teamproject.domain.Dto.response;

import com.example.umc3_teamproject.Forum.repository.Dto.request.ScriptIdsToRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumDataToGetResult {
    private Long userId;
    private Long forumId;
    private String title;
    private String comment;
    private int like_num;
    private List<ScriptIdsToRequest> scriptIdToRequests;
    private List<String> forumImage_url;
}