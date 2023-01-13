package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.CommentRequestDto;
import com.example.umc3_teamproject.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Comment Api"})
@RequestMapping("/forum/{forum-id}/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/new")
    public ResponseEntity<?> createComment(@PathVariable("forum-id") Long forum_id,
                                           @RequestBody @Validated CommentRequestDto.createCommentRequest request){
        return commentService.createComment(forum_id,request);
    }

    @PutMapping("/edit/{comment-id}")
    public ResponseEntity<?> updateComment(@PathVariable("forum-id") Long forum_id,
                                               @PathVariable("comment-id") Long comment_id,
                                               @RequestBody @Validated CommentRequestDto.updateCommentRequest request){
        return commentService.updateComment(comment_id,request);
    }

    @DeleteMapping("delete/{comment-id}")
    public String deletecomment(@PathVariable("forum-id") Long forum_id,
                                @PathVariable("comment-id") Long comment_id){
        return commentService.deleteComment(forum_id,comment_id);
    }

    @GetMapping("/{comment-id}")
    public ResponseEntity<?> getCommentByCommentId(@PathVariable("forum-id") Long forum_id,
                                           @PathVariable("comment-id") Long comment_id){
        return commentService.getCommentByCommentId(forum_id,comment_id);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCommentByForumId(@PathVariable("forum-id") Long forum_id){
        return commentService.getAllByForumId(forum_id);
    }
}
