package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.NestedCommentRequestDto;
import com.example.umc3_teamproject.domain.dto.response.CommentResponseDto;
import com.example.umc3_teamproject.domain.dto.response.NestedCommentResponseDto;
import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.NestedComment;


import com.example.umc3_teamproject.exception.*;

import com.example.umc3_teamproject.repository.CommentRepository;
import com.example.umc3_teamproject.repository.NestedCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NestedCommentService {
    private final NestedCommentRepository nestedCommentRepository;
    private final CommentRepository commentRepository;

    private final MemberService memberService;

    @Transactional
    public Long join(Comment comment){
        commentRepository.save(comment);
        return comment.getId();
    }

    //대댓글 하나 조회
    public NestedComment findOne(Long nestedComment_id){
        return nestedCommentRepository.findById(nestedComment_id).get();
    }

    //모든 대댓글 조회
    public List<NestedComment> findAll(){
        return nestedCommentRepository.findAll();
    }

    // 해당 댓글 id에 해당하는 대댓글 조회
    public List<NestedComment> findAllByCommentId(Long comment_id){return nestedCommentRepository.findByCommentId(comment_id);}

    @Transactional
    public ResponseTemplate<NestedCommentResponseDto.Body> createNestedComment (Long comment_id, NestedCommentRequestDto.createNestedCommentRequest request){
        Comment findComment = commentRepository.findById(comment_id).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        Member writer = memberService.findById(request.getUser_id());
        if(writer == null){
            throw new CustomException(ErrorCode.Member_NOT_FOUND);
        }
        NestedComment nestedComment = new NestedComment();
        nestedComment.createNestedComment(request.getContent(),findComment,writer);
        nestedCommentRepository.save(nestedComment);
        return new ResponseTemplate<>(new NestedCommentResponseDto.Body(nestedComment));

    }

    @Transactional
    public ResponseTemplate<NestedCommentResponseDto.Body> updateNestedComment(Long nestedComment_id, NestedCommentRequestDto.updateNestedCommentRequest request){
        NestedComment findNestedComment = nestedCommentRepository.findById(nestedComment_id).orElseThrow(
                ()->new CustomException(ErrorCode.NESTED_COMMENT_NOT_FOUND)
        );
        findNestedComment.updateNestedComment(request.getContent());
        return new ResponseTemplate<>(new NestedCommentResponseDto.Body(findNestedComment));

    }

    @Transactional
    public String deleteNestedComment(Long nestedComment_id){
        NestedComment findNestedComment = nestedCommentRepository.findById(nestedComment_id).orElseThrow(
                ()->new CustomException(ErrorCode.NESTED_COMMENT_NOT_FOUND)
        );
        findNestedComment.deleteComment();
        return "nested_comment_id " +nestedComment_id +  " 삭제 성공";
    }

    public ResponseTemplate<NestedCommentResponseDto.Body> getNestedCommentByNestedCommentId(Long nestedComment_id) {
        NestedComment findNestedComment = nestedCommentRepository.findById(nestedComment_id).orElseThrow(
                ()->new CustomException(ErrorCode.NESTED_COMMENT_NOT_FOUND)
        );
        NestedCommentResponseDto.Body createNestedCommentResponse = new NestedCommentResponseDto.Body(findNestedComment);
        return new ResponseTemplate<>(createNestedCommentResponse);

    }

    public ResponseTemplate<List<NestedCommentResponseDto.Body>> getAllByCommentId(Long comment_id) {
        Comment findComment = commentRepository.findById(comment_id).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
        List<NestedComment> nestedComments = nestedCommentRepository.findNestedCommentWithMemberCommentByCommentId(comment_id);
        List<NestedCommentResponseDto.Body> nestedCommentDataToGetResults = nestedComments.stream().map(
                        s -> new NestedCommentResponseDto.Body(s))
                .collect(Collectors.toList());
        return new ResponseTemplate<>(nestedCommentDataToGetResults);
    }

    @Transactional
    public ResponseTemplate<NestedCommentResponseDto.LikeResponseDto> likePlus(Long comment_id) throws ResponseException {
        NestedComment findNestedComment = nestedCommentRepository.findById(comment_id).orElseThrow(
                () -> new CustomException(ErrorCode.NESTED_COMMENT_NOT_FOUND)
        );
        findNestedComment.likePlus();
        return new ResponseTemplate<>(new NestedCommentResponseDto.LikeResponseDto(findNestedComment.getId(),findNestedComment.getLike_num())) ;
    }

    public ResponseTemplate<NestedCommentResponseDto.LikeResponseDto> getLike(Long comment_id) throws ResponseException {
        NestedComment findNestedComment = nestedCommentRepository.findById(comment_id).orElseThrow(
                () -> new CustomException(ErrorCode.NESTED_COMMENT_NOT_FOUND)
        );
        return new ResponseTemplate<>(new NestedCommentResponseDto.LikeResponseDto(findNestedComment.getId(),findNestedComment.getLike_num()));
    }

    @Transactional
    public ResponseTemplate<NestedCommentResponseDto.LikeResponseDto> likeMinus(Long comment_id) throws ResponseException {
        NestedComment findNestedComment = nestedCommentRepository.findById(comment_id).orElseThrow(
                () -> new CustomException(ErrorCode.NESTED_COMMENT_NOT_FOUND)
        );
        findNestedComment.likeMinus();
        return new ResponseTemplate<>(new NestedCommentResponseDto.LikeResponseDto(findNestedComment.getId(),findNestedComment.getLike_num()));
    }
}