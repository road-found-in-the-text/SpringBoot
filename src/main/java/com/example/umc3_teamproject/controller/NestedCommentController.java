package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.createNestedCommentRequest;
import com.example.umc3_teamproject.domain.dto.request.updateNestedCommentRequest;
import com.example.umc3_teamproject.domain.dto.response.createNestedCommentResponse;
import com.example.umc3_teamproject.service.NestedCommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Comment Api"})
public class NestedCommentController {
    private final NestedCommentService nestedCommentService;

    @PostMapping("/comment/{comment-id}/nested-comment")
    public createNestedCommentResponse createComment(@PathVariable("comment-id") Long comment_id,
                                                     @RequestBody @Validated createNestedCommentRequest request){
        return nestedCommentService.createNestedComment(comment_id,request);
    }

    @PutMapping("/comment/{comment-id}/nested-comment/{nested-comment-id}")
    public createNestedCommentResponse updateComment(@PathVariable("comment-id") Long comment_id,
                                               @PathVariable("nested-comment-id") Long nestedComment_id,
                                               @RequestBody @Validated updateNestedCommentRequest request){
        return nestedCommentService.updateNestedComment(nestedComment_id,request);
    }

    @DeleteMapping("/comment/{comment-id}/nested-comment/{nested-comment-id}")
    public String deleteNestedComment(@PathVariable("comment-id") Long comment_id,
                                @PathVariable("nested-comment-id") Long nestedComment_id){
        return nestedCommentService.deleteNestedComment(nestedComment_id);
    }

    @GetMapping("/comment/{comment-id}/nested-comment/{nested-comment-id}")
    public GetResult getNestedCommentByNestedCommentId(@PathVariable("comment-id") Long comment_id,
                                                       @PathVariable("nested-comment-id") Long nestedComment_id){
        return nestedCommentService.getNestedCommentByNestedCommentId(nestedComment_id);
    }

    @GetMapping("/comment/{comment-id}/nested-comment")
    public GetResult getAllNestedCommentByCommentId(@PathVariable("comment-id") Long comment_id){
        return nestedCommentService.getAllByCommentId(comment_id);
    }
}
