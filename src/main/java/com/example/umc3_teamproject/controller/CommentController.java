package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponsePageTemplate;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.request.CommentRequestDto;
import com.example.umc3_teamproject.domain.dto.response.CommentResponseDto;
import com.example.umc3_teamproject.domain.dto.response.ForumResponseDto;
import com.example.umc3_teamproject.service.CommentService;
import com.example.umc3_teamproject.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Comment Api"})
@RequestMapping("/forum/{forum-id}/comment")
public class CommentController {
    private final CommentService commentService;
    private final JwtService jwtService;

    @ApiOperation(value = "댓글 create")
    @ResponseBody
    @PostMapping("/new")
    public ResponseTemplate<CommentResponseDto.Body> createComment(@PathVariable("forum-id") Long forum_id,
                                             @RequestBody @Validated CommentRequestDto.createCommentRequest request) throws ResponseException {
        Long writer_id = jwtService.getmemberId();
        return commentService.createComment(forum_id,writer_id,request);
    }
    @ApiOperation(value = "해당 comment-id인 댓글 update")
    @ResponseBody
    @PutMapping("/edit/{comment-id}")
    public ResponseTemplate<CommentResponseDto.Body> updateComment(@PathVariable("forum-id") Long forum_id,
                                                                   @PathVariable("comment-id") Long comment_id,
                                                                   @RequestBody @Validated CommentRequestDto.updateCommentRequest request){
        return commentService.updateComment(comment_id,request);
    }
    @ApiOperation(value = "해당 comment-id인 댓글 delete")
    @ResponseBody
    @DeleteMapping("delete/{comment-id}")
    public String deletecomment(@PathVariable("forum-id") Long forum_id,
                                @PathVariable("comment-id") Long comment_id) throws ResponseException {
        return commentService.deleteComment(forum_id,comment_id);
    }

    @ApiOperation(value = "해당 comment-id인 댓글 조회")
    @ResponseBody
    @GetMapping("/{comment-id}")
    public ResponseTemplate<CommentResponseDto.Body> getCommentByCommentId(@PathVariable("forum-id") Long forum_id,
                                           @PathVariable("comment-id") Long comment_id) throws ResponseException {
        return commentService.getCommentByCommentId(forum_id,comment_id);
    }

    @ApiOperation(value = "해당 forum-id인 forum에 있는 모든 댓글 조회")
    @ResponseBody
    @GetMapping("")
    public ResponsePageTemplate<List<CommentResponseDto.Body>> getAllCommentByForumId(@PathVariable("forum-id") Long forum_id,
                                                                                      @PageableDefault(size=10, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageable) throws ResponseException {
        return commentService.getAllByForumId(forum_id,pageable);
    }

    @ApiOperation(value = "해당 comment-id인 comment 좋아요 수 하나 증가")
    @PutMapping("/{comment-id}/like/plus")
    public ResponseTemplate<CommentResponseDto.LikeResponseDto> likePlus(@PathVariable("comment-id") Long comment_id) throws ResponseException {
        return commentService.likePlus(comment_id);
    }

    @ApiOperation(value = "해당 comment-id인 comment 좋아요 수 하나 감소")
    @PutMapping("/{comment-id}/like/minus")
    public ResponseTemplate<CommentResponseDto.LikeResponseDto> likeMinus(@PathVariable("comment-id") Long comment_id) throws ResponseException {
        return commentService.likeMinus(comment_id);
    }

    @ApiOperation(value = "해당 comment-id인 comment 좋아요 수 조회")
    @GetMapping("/{comment-id}/like")
    public ResponseTemplate<CommentResponseDto.LikeResponseDto> getCommentLike(@PathVariable("comment-id") Long comment_id) throws ResponseException {
        return commentService.getLike(comment_id);
    }
}
