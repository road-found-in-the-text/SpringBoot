package com.example.umc3_teamproject.Forum.controller;

import com.example.umc3_teamproject.Forum.domain.Forum;
import com.example.umc3_teamproject.Forum.repository.Dto.GetResult;
import com.example.umc3_teamproject.Forum.repository.Dto.request.ScriptIdsToRequest;
import com.example.umc3_teamproject.Forum.repository.Dto.response.ForumDataToGetResult;
import com.example.umc3_teamproject.Forum.repository.Dto.ForumSearchById;
import com.example.umc3_teamproject.Forum.repository.Dto.request.createForumRequest;
import com.example.umc3_teamproject.Forum.repository.Dto.request.updateForumRequest;
import com.example.umc3_teamproject.Forum.repository.Dto.response.createForumResponse;
import com.example.umc3_teamproject.Forum.repository.Dto.response.updateForumResponse;
import com.example.umc3_teamproject.Forum.service.ForumService;
import io.swagger.annotations.Api;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Forum Api"})
public class ForumController {
    private final ForumService forumService;

    @GetMapping("/forum")
    public GetResult getForumAll(){
        List<Forum> forums= forumService.findAll();
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getUser().getId(),s.getId(),s.getTitle(),s.getContent(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
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
        List<Forum> forums= forumService.findAllById(new ForumSearchById(id));
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getUser().getId(),s.getId(),s.getTitle(),s.getContent(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // forum_id로 조회
    @GetMapping("/forum/{user-id}/{forum-id}")
    public GetResult getForumByForumId(@PathVariable("forum-id")Long id){
        Forum forum= forumService.findOne(id);
        ForumDataToGetResult forumDataToGetResult = new ForumDataToGetResult(
                forum.getUser().getId(),
                forum.getId(),
                forum.getTitle(),
                forum.getContent(),
                forum.getForumScripts().stream().map(
                        i -> new ScriptIdsToRequest(i.getScript().getId())
                ).collect(Collectors.toList()),
                forum.getForumImages().stream().map(
                        i -> i.getImageUrl()
                ).collect(Collectors.toList()));
        return new GetResult(1, forumDataToGetResult);
    }

    // forum_id로 조회한 forum 글 수정
    @PutMapping("/forum/{user-id}/{forum-id}")
    public updateForumResponse updateForum(@PathVariable("user-id")  Long user_id, @PathVariable("forum-id")  Long forum_id,
                                           @RequestBody @Validated updateForumRequest request){
        Forum forum = forumService.updateForum(forum_id, request);
        ForumDataToGetResult forumDataToGetResult = new ForumDataToGetResult(forum.getUser().getId(),forum.getId(),forum.getTitle(),
                forum.getContent(),forum.getForumScripts().stream().map(
                        f -> new ScriptIdsToRequest(f.getScript().getId())
        ).collect(Collectors.toList()),
                forum.getForumImages().stream().map(
                        i -> i.getImageUrl()
                ).collect(Collectors.toList()));
        return new updateForumResponse(forumDataToGetResult);
    }

    // 해당 forum_id의 forum 글을 삭제(forum_id를 통해서 삭제)
    @DeleteMapping("/forum/{user-id}/{forum-id}")
    public String deleteForum(@PathVariable("user-id")  Long user_id,@PathVariable("forum-id")  Long forum_id){
         return forumService.deleteForum(forum_id);
    }

    // script가 포함된 forum 모두 가져오기
    @GetMapping("/forum/script")
    public GetResult getForumByScript(){
        List<Forum> forums= forumService.findAllByScript();
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getUser().getId(),s.getId(),s.getTitle(),s.getContent(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // interview가 포함된 forum 모두 가져오기
    @GetMapping("/forum/interview")
    public GetResult getForumByInterview(){
        List<Forum> forums= forumService.findAllByInterview();
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getUser().getId(),s.getId(),s.getTitle(),s.getContent(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

    // script, interview가 없는 forum 글 모두 가져오기
    @GetMapping("/forum/free")
    public GetResult getForum_No_script_No_interview(){
        List<Forum> forums= forumService.findAllByFree();
        List<ForumDataToGetResult> forumDataToGetResultRespons = forums.stream().map(
                        s -> new ForumDataToGetResult(s.getUser().getId(),s.getId(),s.getTitle(),s.getContent(),
                                s.getForumScripts().stream().map(i -> new ScriptIdsToRequest(i.getScript().getId())).collect(Collectors.toList()),
                                s.getForumImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(forumDataToGetResultRespons.size(), forumDataToGetResultRespons);
    }

}
