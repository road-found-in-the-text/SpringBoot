package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.NestedCommentRequestDto;
import com.example.umc3_teamproject.domain.dto.response.NestedCommentResponseDto;
import com.example.umc3_teamproject.service.NestedCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"NestedComment Api"})
@RequestMapping("/comment/{comment-id}/nested-comment")
public class NestedCommentController {
    private final NestedCommentService nestedCommentService;

    @ApiOperation(value = "해당 comment-id인 댓글에 대한 대댓글 create")
    @ResponseBody
    @PostMapping("/new")
    public ResponseTemplate<NestedCommentResponseDto.Body> createNestedComment(@PathVariable("comment-id") Long comment_id,
                                                   @RequestBody @Validated NestedCommentRequestDto.createNestedCommentRequest request){
        return nestedCommentService.createNestedComment(comment_id,request);
    }
    @ApiOperation(value = "해당 nested-comment-id인 대댓글 update")
    @ResponseBody
    @PutMapping("/edit/{nested-comment-id}")
    public ResponseTemplate<NestedCommentResponseDto.Body> updateComment(@PathVariable("comment-id") Long comment_id,
                                                                    @PathVariable("nested-comment-id") Long nestedComment_id,
                                                                    @RequestBody @Validated NestedCommentRequestDto.updateNestedCommentRequest request){
        return nestedCommentService.updateNestedComment(nestedComment_id,request);
    }
    @ApiOperation(value = "해당 nested-comment-id인 대댓글 delete")
    @ResponseBody
    @DeleteMapping("/delete/{nested-comment-id}")
    public String deleteNestedComment(@PathVariable("comment-id") Long comment_id,
                                @PathVariable("nested-comment-id") Long nestedComment_id){
        return nestedCommentService.deleteNestedComment(nestedComment_id);
    }
    @ApiOperation(value = "해당 nested-comment-id인 대댓글 조회")
    @ResponseBody
    @GetMapping("/{nested-comment-id}")
    public ResponseTemplate<NestedCommentResponseDto.Body> getNestedCommentByNestedCommentId(@PathVariable("comment-id") Long comment_id,
                                                       @PathVariable("nested-comment-id") Long nestedComment_id){
        return nestedCommentService.getNestedCommentByNestedCommentId(nestedComment_id);
    }

    @ApiOperation(value = "해당 comment-id인 댓글에 포함된 대댓글 모두 조회")
    @ResponseBody
    @GetMapping("")
    public ResponseTemplate<List<NestedCommentResponseDto.Body>> getAllNestedCommentByCommentId(@PathVariable("comment-id") Long comment_id){
        return nestedCommentService.getAllByCommentId(comment_id);
    }
}
