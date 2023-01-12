package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
import com.example.umc3_teamproject.service.ForumService;
import io.swagger.annotations.Api;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Forum Api"})
@RequestMapping("/forum")
public class ForumController {
    private final ForumService forumService;

    @GetMapping("")
    public ResponseEntity<?> getForumAll(){
        return forumService.getForumAll();
    }

    // user_id로 forum 생성 (해당 회원의 forum을 생성한다.)
    // 나중에는 jwt를 사용한 회원 아이디를 자동으로 가져올거기 때문에 pathvariable을 사용할 필요 없을 것 같다.
    @PostMapping("/new/{user-id}")
    public ResponseEntity<?> createForum(@PathVariable("user-id") Long user_id
            , @ModelAttribute ForumRequestDto.createForumRequest request) throws IOException {
        return forumService.createForum(user_id,request);
    }

    // user_id로 조회
    @GetMapping("/user/{user-id}")
    public ResponseEntity<?> getForumByUserId(@PathVariable("user-id")Long user_id){
        return forumService.getForumByUserId(user_id);
    }

    // forum_id로 조회
    @GetMapping("/{forum-id}")
    public ResponseEntity<?> getForumByForumId(@PathVariable("forum-id")Long id){
        return forumService.getForumByForumId(id);
    }

    // forum_id로 조회한 forum 글 수정
    @PutMapping("/edit/{forum-id}")
    public ResponseEntity<?> updateForum(@PathVariable("forum-id")  Long forum_id,
                                 @ModelAttribute ForumRequestDto.updateForumRequest request){
        return forumService.updateForum(forum_id,request);
    }

    // 해당 forum_id의 forum 글을 삭제(forum_id를 통해서 삭제)
    @DeleteMapping("/delete/{forum-id}")
    public String deleteForum(@PathVariable("forum-id")  Long forum_id){
        return forumService.deleteForum(forum_id);
    }

    // /forum?type = "script"       -> script 조회
//    @GetMapping("/forums")
//    public ResponseEntity<?> getForumByType(@PathParam("type") String type){
//        return forumService.getForumByType(type);
//    }

    // script가 포함된 forum 모두 가져오기
    @GetMapping("/script")
    public ResponseEntity<?> getForumByScript(){
        return forumService.getForumByScript();
    }

    // interview가 포함된 forum 모두 가져오기
    @GetMapping("/interview")
    public ResponseEntity<?> getForumByInterview(){
        return forumService.getForumByInterview();
    }

    // script, interview가 없는 forum 글 모두 가져오기
    @GetMapping("/free")
    public ResponseEntity<?> getForum_No_script_No_interview(){
        return forumService.getForum_No_script_No_interview();
    }

    // /forum/search?title = "sldkjf"
    @GetMapping("/search")
    public ResponseEntity<?> SearchAllByTitle(@RequestParam("q") String search_keyword){
        return forumService.SearchAllByKeyword(search_keyword);
    }

    @PutMapping("/{forum-id}/like/plus")
    public ResponseEntity<?> likePlus(@PathVariable("forum-id") Long forum_id){
        return forumService.likePlus(forum_id);
    }

    @PutMapping("/{forum-id}/like/minus")
    public ResponseEntity<?> likeMinus(@PathVariable("forum-id") Long forum_id){
        return forumService.likeMinus(forum_id);
    }

    @GetMapping("/{forum-id}/like")
    public ResponseEntity<?> getForumLike(@PathVariable("forum-id") Long forum_id){
        return forumService.getLike(forum_id);
    }

}
