package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.NestedCommentRequestDto;
import com.example.umc3_teamproject.service.NestedCommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = {"NestedComment Api"})
@RequestMapping("/comment/{comment-id}/nested-comment")
public class NestedCommentController {
    private final NestedCommentService nestedCommentService;

    @PostMapping("/new")
    public ResponseEntity<?> createComment(@PathVariable("comment-id") Long comment_id,
                                           @RequestBody @Validated NestedCommentRequestDto.createNestedCommentRequest request){
        return nestedCommentService.createNestedComment(comment_id,request);
    }

    @PutMapping("/edit/{nested-comment-id}")
    public ResponseEntity<?> updateComment(@PathVariable("comment-id") Long comment_id,
                                               @PathVariable("nested-comment-id") Long nestedComment_id,
                                               @RequestBody @Validated NestedCommentRequestDto.updateNestedCommentRequest request){
        return nestedCommentService.updateNestedComment(nestedComment_id,request);
    }

    @DeleteMapping("/delete/{nested-comment-id}")
    public String deleteNestedComment(@PathVariable("comment-id") Long comment_id,
                                @PathVariable("nested-comment-id") Long nestedComment_id){
        return nestedCommentService.deleteNestedComment(nestedComment_id);
    }

    @GetMapping("/{nested-comment-id}")
    public ResponseEntity<?> getNestedCommentByNestedCommentId(@PathVariable("comment-id") Long comment_id,
                                                       @PathVariable("nested-comment-id") Long nestedComment_id){
        return nestedCommentService.getNestedCommentByNestedCommentId(nestedComment_id);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllNestedCommentByCommentId(@PathVariable("comment-id") Long comment_id){
        return nestedCommentService.getAllByCommentId(comment_id);
    }
}
