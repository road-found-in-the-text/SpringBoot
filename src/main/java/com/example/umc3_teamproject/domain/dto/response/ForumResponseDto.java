package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
import lombok.*;

import java.util.List;

public class ForumResponseDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ForumDataToGetResult {
        private Long userId;
        private Long forumId;
        private String title;
        private String comment;
        private int like_num;
        private List<ForumRequestDto.ScriptIdsToRequest> scriptIdToRequests;
        private List<String> forumImage_url;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ForumSearchByUserId {
        private Long user_id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeResponseDto {
        private Long forum_id;
        private int like_num;
    }

}
