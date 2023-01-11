package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.NestedCommentRequestDto;
import com.example.umc3_teamproject.domain.dto.response.NestedCommentResponseDto;
import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.Member;
import com.example.umc3_teamproject.domain.item.NestedComment;
import com.example.umc3_teamproject.exception.CommentNotFoundException;
import com.example.umc3_teamproject.exception.MemberNotFoundException;
import com.example.umc3_teamproject.exception.NestedCommentNotFoundException;
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
    public ResponseEntity<?> createNestedComment (Long comment_id, NestedCommentRequestDto.createNestedCommentRequest request){
        Comment findComment = commentRepository.findById(comment_id).orElseThrow(
                () -> new CommentNotFoundException("아이디가 " + comment_id + "인 댓글은 존재하지 않습니다.")
        );

        Member writer = memberService.findById(request.getUser_id());
        if(writer == null){
            throw new MemberNotFoundException("아이디가 " + request.getUser_id() + "인 유저는 존재하지 않습니다.");
        }
        NestedComment nestedComment = new NestedComment();
        nestedComment.createNestedComment(request.getContent(),findComment,writer);
        nestedCommentRepository.save(nestedComment);
        return ResponseEntity.ok(new NestedCommentResponseDto.Body(nestedComment));

    }

    @Transactional
    public ResponseEntity<?> updateNestedComment(Long nestedComment_id, NestedCommentRequestDto.updateNestedCommentRequest request){
        NestedComment findNestedComment = nestedCommentRepository.findById(nestedComment_id).orElseThrow(
                ()->new NestedCommentNotFoundException("아이디가 " + nestedComment_id + "인 대댓글은 존재하지 않습니다.")
        );
        findNestedComment.updateNestedComment(request.getContent());
        return ResponseEntity.ok(new NestedCommentResponseDto.Body(findNestedComment));

    }

    @Transactional
    public String deleteNestedComment(Long nestedComment_id){
        NestedComment findNestedComment = nestedCommentRepository.findById(nestedComment_id).orElseThrow(
                ()->new NestedCommentNotFoundException("아이디가 " + nestedComment_id + "인 대댓글은 존재하지 않습니다.")
        );
        findNestedComment.deleteComment();
        return "comment_id " +nestedComment_id +  " 삭제 성공";
    }

    public ResponseEntity<?> getNestedCommentByNestedCommentId(Long nestedComment_id) {
        NestedComment findNestedComment = nestedCommentRepository.findById(nestedComment_id).orElseThrow(
                ()->new NestedCommentNotFoundException("아이디가 " + nestedComment_id + "인 대댓글은 존재하지 않습니다.")
        );
        NestedCommentResponseDto.Body createNestedCommentResponse = new NestedCommentResponseDto.Body(findNestedComment);
        return ResponseEntity.ok(new GetResult(1,createNestedCommentResponse));

    }

    public ResponseEntity<?> getAllByCommentId(Long comment_id) {
        Comment findComment = commentRepository.findById(comment_id).orElseThrow(
                () -> new CommentNotFoundException("아이디가 " + comment_id + "인 댓글은 존재하지 않습니다.")
        );
        List<NestedComment> nestedComments = nestedCommentRepository.findNestedCommentWithMemberCommentByCommentId(comment_id);
        List<NestedCommentResponseDto.Body> nestedCommentDataToGetResults = nestedComments.stream().map(
                        s -> new NestedCommentResponseDto.Body(s))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new GetResult(nestedCommentDataToGetResults.size(),nestedCommentDataToGetResults));
    }
}
