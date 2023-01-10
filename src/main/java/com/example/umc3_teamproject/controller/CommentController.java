package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.Dto.GetResult;
import com.example.umc3_teamproject.domain.Dto.request.createCommentRequest;
import com.example.umc3_teamproject.domain.Dto.request.updateCommentRequest;
import com.example.umc3_teamproject.domain.Dto.response.createCommentResponse;
import com.example.umc3_teamproject.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Comment Api"})
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/forum/{forum-id}/comment")
    public createCommentResponse createComment(@PathVariable("forum-id") Long forum_id,
                                               @RequestBody @Validated createCommentRequest request){
        return commentService.createComment(forum_id,request);
    }

    @PutMapping("/forum/{forum-id}/comment/{comment-id}")
    public createCommentResponse updateComment(@PathVariable("forum-id") Long forum_id,
                                               @PathVariable("comment-id") Long comment_id,
                                               @RequestBody @Validated updateCommentRequest request){
        return commentService.updateComment(comment_id,request);
    }

    @DeleteMapping("/forum/{forum-id}/comment/{comment-id}")
    public String deletecomment(@PathVariable("forum-id") Long forum_id,
                                @PathVariable("comment-id") Long comment_id){
        return commentService.deleteComment(comment_id);
    }

    @GetMapping("/forum/{forum-id}/comment/{comment-id}")
    public GetResult getCommentByCommentId(@PathVariable("forum-id") Long forum_id,
                                           @PathVariable("comment-id") Long comment_id){
        return commentService.getCommentByCommentId(comment_id);
    }

    @GetMapping("/forum/{forum-id}/comment")
    public GetResult getAllCommentByForumId(@PathVariable("forum-id") Long forum_id){
        return commentService.getAllByForumId(forum_id);
    }
}
