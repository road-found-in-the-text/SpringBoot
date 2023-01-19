package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Api
public class ForumResponseDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ForumDataToGetResult {
        @ApiParam(value = "forum 작성자 id", required = true, example = "1")
        private Long userId;

        @ApiParam(value = "해당 forum id", required = true, example = "1")
        private Long forumId;

        @ApiParam(value = "forum 제목", required = false, example = "제목")
        private String title;

        @ApiParam(value = "forum 내용", required = false, example = "내용")
        private String comment;

        @ApiParam(value = "해당 forum의 좋아요 수", required = false, example = "100")
        private int like_num;

        @ApiParam(value = "해당 forum에 저장된 script의 id (복수 가능)", required = false, example = "")
        private List<ForumRequestDto.ScriptIdsToRequest> scriptIdToRequests;

        @ApiParam(value = "해당 forum에 저장된 interview의 id (복수 가능)", required = false, example = "")
        private List<ForumRequestDto.InterviewIdsToRequest> interviewIdsToRequests;

        @ApiParam(value = "해당 froum에 저장된 이미지 또는 비디오 url (복수 가능)", required = false, example = "1")
        private List<String> forumImage_url;

        @ApiParam(value = "forum 생성일", required = false, example = "")
        private LocalDateTime createDate;

        @ApiParam(value = "forum 수정일", required = false, example = "1")
        private LocalDateTime updateDate;



    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ForumSearchByUserId {
        @ApiParam(value = "찾고자 하는 user의 id", required = true, example = "1")
        private Long user_id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeResponseDto {
        @ApiParam(value = "해당 forum id", required = true, example = "1")
        private Long forum_id;

        @ApiParam(value = "해당 forum의 좋아요 수", required = true, example = "100")
        private int like_num;
    }

}
