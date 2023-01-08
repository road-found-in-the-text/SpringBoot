package com.example.umc3_teamproject.domain.Dto.response;

import com.example.umc3_teamproject.Forum.repository.Dto.request.ScriptIdsToRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createForumResponse {
    private Long forum_id;
    private String title;
    private String type;
    private List<ScriptIdsToRequest> scriptIdToRequests;
    private List<String> forumImage_string;
}
