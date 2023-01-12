package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.createForumRequest;
import com.example.umc3_teamproject.domain.dto.response.LikeResponseDto;
import com.example.umc3_teamproject.domain.dto.response.createForumResponse;
import com.example.umc3_teamproject.service.ForumService;
import io.swagger.annotations.Api;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Forum Api"})
public class ForumController {
    private final ForumService forumService;

    @GetMapping("/forum")
    public GetResult getForumAll(){
        return forumService.getForumAll();
    }

    // user_id로 forum 생성 (해당 회원의 forum을 생성한다.)
    // 나중에는 jwt를 사용한 회원 아이디를 자동으로 가져올거기 때문에 pathvariable을 사용할 필요 없을 것 같다.
    @PostMapping("/forum/{user-id}")
    public createForumResponse createForum(@PathVariable("user-id") Long user_id
            , @ModelAttribute createForumRequest request) throws IOException {
        return forumService.createForum(user_id,request);
    }

    // user_id로 조회
    @GetMapping("/forum/{user-id}")
    public GetResult getForumByUserId(@PathVariable("user-id")Long id){
        return forumService.getForumByUserId(id);
    }

    // forum_id로 조회
    @GetMapping("/forum/{user-id}/{forum-id}")
    public GetResult getForumByForumId(@PathVariable("forum-id")Long id){
        return forumService.getForumByForumId(id);
    }

    // forum_id로 조회한 forum 글 수정
    @PutMapping("/forum/{user-id}/{forum-id}")
    public GetResult updateForum(@PathVariable("user-id")  Long user_id, @PathVariable("forum-id")  Long forum_id,
                                 @ModelAttribute createForumRequest request){
        return forumService.updateForumResult(user_id,forum_id,request);
    }

    // 해당 forum_id의 forum 글을 삭제(forum_id를 통해서 삭제)
    @DeleteMapping("/forum/{user-id}/{forum-id}")
    public String deleteForum(@PathVariable("user-id")  Long user_id,@PathVariable("forum-id")  Long forum_id){
        return forumService.deleteForum(forum_id);
    }

    // /forum?type = "script"       -> script 조회
    @GetMapping("/forums")
    public GetResult getForumByType(@PathParam("type") String type){
        return forumService.getForumByType(type);
    }

    // script가 포함된 forum 모두 가져오기
    @GetMapping("/forum/script")
    public GetResult getForumByScript(){
        return forumService.getForumByScript();
    }

    // interview가 포함된 forum 모두 가져오기
    @GetMapping("/forum/interview")
    public GetResult getForumByInterview(){
        return forumService.getForumByInterview();
    }

    // script, interview가 없는 forum 글 모두 가져오기
    @GetMapping("/forum/free")
    public GetResult getForum_No_script_No_interview(){
        return forumService.getForum_No_script_No_interview();
    }

    // /forum/search?title = "sldkjf"

    @PutMapping("/forum/like/plus/{forum-id}")
    public LikeResponseDto likePlus(@PathVariable("forum-id") Long forum_id){
        return forumService.likePlus(forum_id);
    }

    @PutMapping("/forum/like/minus/{forum-id}")
    public LikeResponseDto likeMinus(@PathVariable("forum-id") Long forum_id){
        return forumService.likeMinus(forum_id);
    }

    @GetMapping("/forum/like/{forum-id}")
    public LikeResponseDto getForumLike(@PathVariable("forum-id") Long forum_id){
        return forumService.getLike(forum_id);
    }

}
