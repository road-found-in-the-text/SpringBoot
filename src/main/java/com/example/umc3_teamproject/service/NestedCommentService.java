package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.domain.Comment;
import com.example.umc3_teamproject.domain.Dto.GetResult;
import com.example.umc3_teamproject.domain.Dto.request.createNestedCommentRequest;
import com.example.umc3_teamproject.domain.Dto.request.updateNestedCommentRequest;
import com.example.umc3_teamproject.domain.Dto.response.NestedCommentDataToGetResult;
import com.example.umc3_teamproject.domain.Dto.response.createNestedCommentResponse;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.NestedComment;
import com.example.umc3_teamproject.repository.CommentRepository;
import com.example.umc3_teamproject.repository.NestedCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public createNestedCommentResponse createNestedComment (Long comment_id, createNestedCommentRequest request){
        Comment findComment = commentRepository.findById(comment_id).get();
        Member writer = memberService.findById(request.getUser_id());
        NestedComment nestedComment = new NestedComment();
        nestedComment.createNestedComment(request.getContent(),findComment,writer);
        nestedCommentRepository.save(nestedComment);
        return new createNestedCommentResponse(nestedComment.getId(),findComment.getId(),writer.getNickName(),request.getContent());
    }

    @Transactional
    public createNestedCommentResponse updateNestedComment(Long nestedComment_id, updateNestedCommentRequest request){
        NestedComment findNestedComment = nestedCommentRepository.findById(nestedComment_id).get();
        findNestedComment.updateNestedComment(request.getContent());
        return new createNestedCommentResponse(findNestedComment.getId(),findNestedComment.getComment().getId(),findNestedComment.getMember().getNickName(),
                findNestedComment.getContent());
    }

    @Transactional
    public String deleteNestedComment(Long nestedComment_id){
        NestedComment findNestedComment = nestedCommentRepository.findById(nestedComment_id).get();
        findNestedComment.deleteComment();
        return "comment_id " +nestedComment_id +  " 삭제 성공";
    }

    public GetResult getNestedCommentByNestedCommentId(Long nestedComment_id) {
        NestedComment findNestedComment = nestedCommentRepository.findById(nestedComment_id).get();
        createNestedCommentResponse createNestedCommentResponse = new createNestedCommentResponse(findNestedComment.getId(), findNestedComment.getComment().getId(), findNestedComment.getMember().getNickName(),
                findNestedComment.getContent());
        return new GetResult(1,createNestedCommentResponse);
    }

    public GetResult getAllByCommentId(Long comment_id) {
        List<NestedComment> nestedComments = nestedCommentRepository.findByCommentId(comment_id).stream().map(
                comment -> comment
        ).collect(Collectors.toList());
        List<NestedCommentDataToGetResult> nestedCommentDataToGetResults = nestedComments.stream().map(
                        s -> new NestedCommentDataToGetResult(s.getMember().getId(),s.getComment().getId(),
                                s.getId(),s.getContent(),s.getLike_num()))
                .collect(Collectors.toList());
        return new GetResult(nestedCommentDataToGetResults.size(),nestedCommentDataToGetResults);
    }
}
