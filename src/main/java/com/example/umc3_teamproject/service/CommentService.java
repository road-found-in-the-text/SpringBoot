package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.dto.request.CommentRequestDto;

import com.example.umc3_teamproject.domain.dto.response.CommentResponseDto;
import com.example.umc3_teamproject.domain.dto.response.NestedCommentResponseDto;
import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.Forum;

import com.example.umc3_teamproject.exception.*;
import com.example.umc3_teamproject.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final ForumService forumService;
    private final CommentRepository commentRepository;

    private final MemberService memberService;

    @Transactional
    public Long join(Comment comment){
        commentRepository.save(comment);
        return comment.getId();
    }

    //forum 하나 조회
    public Comment findOne(Long comment_id){
        return commentRepository.findById(comment_id).get();
    }

    //모든 forum 조회
    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    // forum으로 조회
    public List<Comment> findAllByForumId(Long forum_id){return commentRepository.findByForumId(forum_id);}

    @Transactional
    public ResponseTemplate<CommentResponseDto.Body> createComment (Long forum_id, CommentRequestDto.createCommentRequest request) throws ResponseException {
        Forum findForum = forumService.findOne(forum_id);
        Member writer = memberService.findById(request.getUser_id());
        if(writer == null){
            throw new CustomException(ErrorCode.Member_NOT_FOUND);
        }
        Comment comment = new Comment();
        comment.createComment(request.getContent(),findForum,writer);
        commentRepository.save(comment);
        return new ResponseTemplate<>(new CommentResponseDto.Body(comment,null));
//                CommentResponseDto.success(comment,null);
//                new CommentResponseDto.createCommentResponse(comment.getId(),findForum.getId(),writer.getNickName(),request.getContent());
    }

    @Transactional
    public ResponseTemplate<CommentResponseDto.Body> updateComment(Long comment_id, CommentRequestDto.updateCommentRequest request){
        Comment findComment = commentRepository.findById(comment_id).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
        findComment.updateComment(request.getContent());
        return new ResponseTemplate<>(new CommentResponseDto.Body(findComment,
                findComment.getNestedComments().stream().map(
                        nested_comment -> new NestedCommentResponseDto.Body(nested_comment
                        )).collect(Collectors.toList())));
    }

    @Transactional
    public String deleteComment(Long forum_id,Long comment_id) throws ResponseException {
        Forum findForum = forumService.findOne(forum_id);
        Comment findComment = commentRepository.findById(comment_id).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
        findComment.deleteComment();
        return "comment_id " +comment_id +  " 삭제 성공";
    }

    public ResponseTemplate<CommentResponseDto.Body> getCommentByCommentId(Long forum_id,Long comment_id) throws ResponseException {
        Forum findForum = forumService.findOne(forum_id);
        Comment findComment = commentRepository.findById(comment_id).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
        CommentResponseDto.Body commentDataToGetResult =
                new CommentResponseDto.Body(
                        findComment,
                        findComment.getNestedComments().stream().map(
                                nested_comment -> new NestedCommentResponseDto.Body(nested_comment
                                )).collect(Collectors.toList()));
        return new ResponseTemplate<>(commentDataToGetResult);
    }

    public ResponseTemplate<List<CommentResponseDto.Body>> getAllByForumId(Long forum_id) throws ResponseException {
        Forum findForum = forumService.findOne(forum_id);
        List<Comment> comments = commentRepository.findCommentWithMemberForumByForumId(forum_id);
        List<CommentResponseDto.Body> commentDataToGetResults = comments.stream().map(
                        comment -> new CommentResponseDto.Body(comment,
                                comment.getNestedComments().stream().map(
                                        nested_comment -> new NestedCommentResponseDto.Body(nested_comment))
                                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new ResponseTemplate<>(commentDataToGetResults);

    }
}
