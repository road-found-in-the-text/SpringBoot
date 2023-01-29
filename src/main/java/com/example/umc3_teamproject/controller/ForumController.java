package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponsePageTemplate;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ForumResponseDto;
import com.example.umc3_teamproject.service.ForumService;
import com.example.umc3_teamproject.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.netty.http.server.HttpServerRequest;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Forum Api"})
@RequestMapping("/forum")
public class ForumController {
    private final ForumService forumService;

    private final JwtService jwtService;


    // user_id로 forum 생성 (해당 회원의 forum을 생성한다.)
    // 나중에는 jwt를 사용한 회원 아이디를 자동으로 가져올거기 때문에 pathvariable을 사용할 필요 없을 것 같다.
    @ApiOperation(value = "forum 생성", notes = "게시글을 create한다.")
    @PostMapping("/new")
    public ResponseTemplate<ForumResponseDto.ForumDataToGetResult> createForum(@ModelAttribute ForumRequestDto.createForumRequest request, HttpServerRequest serverRequest) throws IOException, ResponseException {
        Long user_id = jwtService.getmemberId();
        return forumService.createForum(user_id,request);
    }

    // forum_id로 조회한 forum 글 수정
    @ApiOperation(value = "해당 forum-id인 forum 수정")
    @ResponseBody
    @PutMapping("/edit/{forum-id}")
    public ResponseTemplate<ForumResponseDto.ForumDataToGetResult> updateForum(@PathVariable("forum-id")  Long forum_id,
                                                                               @ModelAttribute ForumRequestDto.updateForumRequest request) throws ResponseException {
        return forumService.updateForum(forum_id,request);
    }

    // 해당 forum_id의 forum 글을 삭제(forum_id를 통해서 삭제)
    @ApiOperation(value = "해당 forum-id인 forum 삭제")
    @DeleteMapping("/delete/{forum-id}")
    public ResponseTemplate<String> deleteForum(@PathVariable("forum-id")  Long forum_id) throws ResponseException {
        return forumService.deleteForum(forum_id);
    }


    // 모든 forum 조회
    @ApiOperation(value = "전체 forum 조회", notes = "<big>전체 forum</big>을 반환한다.")
    @ResponseBody
    @GetMapping("")
    public ResponsePageTemplate<List<ForumResponseDto.ForumDataToGetResult>> getForumAll(
            @PageableDefault(size=10, sort="created_date", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return forumService.getForumAll(pageable);
    }

    // user_id로 조회
    @ApiOperation(value = "user-id의 사용자가 만든 모든 forum 조회", notes = "user-id의 사용자가 만든 모든 forum 조회")
    @ResponseBody
    @GetMapping("/user/{user-id}")
    public ResponseTemplate<List<ForumResponseDto.ForumDataToGetResult>> getForumByUserId(@PathVariable("user-id")Long user_id) throws ResponseException {
        return forumService.getForumByUserId(user_id);
    }

    // forum_id로 조회
    @ApiOperation(value = "해당 forum-id인 forum 하나 조회",notes = "해당 forum-id인 forum 하나 조회")
    @ResponseBody
    @GetMapping("/{forum-id}")
    public ResponseTemplate<ForumResponseDto.ForumDataToGetResult> getForumByForumId(@PathVariable("forum-id")Long id) throws ResponseException {
        return forumService.getForumByForumId(id);
    }

    // /forum?type = "script"       -> script 조회
//    @GetMapping("/forums")
//    public ResponseEntity<?> getForumByType(@PathParam("type") String type){
//        return forumService.getForumByType(type);
//    }

    // script가 포함된 forum 모두 가져오기
    @ApiOperation(value = "script가 포함된 모든 forum 조회")
    @ResponseBody
    @GetMapping("/script")
    public ResponsePageTemplate<List<ForumResponseDto.ForumDataToGetResult>> getForumByScript(
            @PageableDefault(size=10, sort="created_date", direction = Sort.Direction.DESC) Pageable pageable){
        return forumService.getForumByScript(pageable);
    }

    // interview가 포함된 forum 모두 가져오기
    @ApiOperation(value = "interview가 포함된 모든 forum 조회")
    @ResponseBody
    @GetMapping("/interview")
    public ResponsePageTemplate<List<ForumResponseDto.ForumDataToGetResult>> getForumByInterview(
            @PageableDefault(size=10, sort="created_date", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return forumService.getForumByInterview(pageable);
    }

    // script, interview가 없는 forum 글 모두 가져오기
    @ApiOperation(value = "script와 interview가 없는 모든 forum 조회")
    @ResponseBody
    @GetMapping("/free")
    public ResponsePageTemplate<List<ForumResponseDto.ForumDataToGetResult>> getForum_No_script_No_interview(
            @PageableDefault(size=10, sort="created_date", direction = Sort.Direction.DESC) Pageable pageable){
        return forumService.getForum_No_script_No_interview(pageable);
    }

    @GetMapping("/search")
    public ResponsePageTemplate<List<ForumResponseDto.ForumDataToGetResult>> SearchAllByTitle(@RequestParam("q") String search_keyword
            ,@PageableDefault(size=10, sort="created_date", direction = Sort.Direction.DESC) Pageable pageable){
        return forumService.SearchAllByKeyword(search_keyword,pageable);
    }

    // /forum/search?title = "sldkjf"
    @ApiOperation(value = "해당 forum-id인 forum 좋아요 수 하나 증가")
    @PutMapping("/{forum-id}/like/plus")
    public ResponseTemplate<ForumResponseDto.LikeResponseDto> likePlus(@PathVariable("forum-id") Long forum_id) throws ResponseException {
        return forumService.likePlus(forum_id);
    }

    @ApiOperation(value = "해당 forum-id인 forum 좋아요 수 하나 감소")
    @PutMapping("/{forum-id}/like/minus")
    public ResponseTemplate<ForumResponseDto.LikeResponseDto> likeMinus(@PathVariable("forum-id") Long forum_id) throws ResponseException {
        return forumService.likeMinus(forum_id);
    }

    @ApiOperation(value = "해당 forum-id인 forum 좋아요 수 조회")
    @GetMapping("/{forum-id}/like")
    public ResponseTemplate<ForumResponseDto.LikeResponseDto> getForumLike(@PathVariable("forum-id") Long forum_id) throws ResponseException {
        return forumService.getLike(forum_id);
    }

    @GetMapping("/bestForum")
    public ResponseTemplate<List<ForumResponseDto.ForumDataToGetResult>> getSixForumByLikeDesc(){
        return forumService.getSixForumByLikeDesc();
    }

}
