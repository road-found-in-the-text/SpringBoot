package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.NestedCommentRequestDto;
import com.example.umc3_teamproject.domain.dto.response.CommentResponseDto;
import com.example.umc3_teamproject.domain.dto.response.NestedCommentResponseDto;
import com.example.umc3_teamproject.service.JwtService;
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
    private final JwtService jwtService;

    @ApiOperation(value = "해당 comment-id인 댓글에 대한 대댓글 create")
    @ResponseBody
    @PostMapping("/new")
    public ResponseTemplate<NestedCommentResponseDto.Body> createNestedComment(@PathVariable("comment-id") Long comment_id,
                                                   @RequestBody @Validated NestedCommentRequestDto.createNestedCommentRequest request) throws ResponseException {
        Long writer_id = jwtService.getmemberId();
        return nestedCommentService.createNestedComment(comment_id,writer_id,request);
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

    @ApiOperation(value = "해당 nested-comment-id인 대댓글 좋아요 수 하나 증가")
    @PutMapping("/{nested-comment-id}/like/plus")
    public ResponseTemplate<NestedCommentResponseDto.LikeResponseDto> likePlus(@PathVariable("nested-comment-id") Long nested_comment_id) throws ResponseException {
        return nestedCommentService.likePlus(nested_comment_id);
    }

    @ApiOperation(value = "해당 nested-comment-id인 대댓글 좋아요 수 하나 감소")
    @PutMapping("/{nested-comment-id}/like/minus")
    public ResponseTemplate<NestedCommentResponseDto.LikeResponseDto> likeMinus(@PathVariable("nested-comment-id") Long nested_comment_id) throws ResponseException {
        return nestedCommentService.likeMinus(nested_comment_id);
    }

    @ApiOperation(value = "해당 nested-comment-id인 대댓글 좋아요 수 조회")
    @GetMapping("/{nested-comment-id}/like")
    public ResponseTemplate<NestedCommentResponseDto.LikeResponseDto> getNestedCommentLike(@PathVariable("nested-comment-id") Long nested_comment_id) throws ResponseException {
        return nestedCommentService.getLike(nested_comment_id);
    }
}
