package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.CommentRequestDto;

import com.example.umc3_teamproject.domain.dto.response.CommentResponseDto;
import com.example.umc3_teamproject.domain.dto.response.NestedCommentResponseDto;
import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.Forum;
import com.example.umc3_teamproject.domain.item.Member;
import com.example.umc3_teamproject.exception.CommentNotFoundException;
import com.example.umc3_teamproject.exception.ForumNotFoundException;
import com.example.umc3_teamproject.exception.MemberNotFoundException;
import com.example.umc3_teamproject.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createComment (Long forum_id, CommentRequestDto.createCommentRequest request){
        Forum findForum = forumService.findOne(forum_id);
        if(findForum == null){
            throw new ForumNotFoundException("아이디가 " + forum_id + "인 forum은 존재하지 않습니다.");
        }
        Member writer = memberService.findById(request.getUser_id());
        if(writer == null){
            throw new MemberNotFoundException("아이디가 " + request.getUser_id() + "인 user는 존재하지 않습니다.");
        }
        Comment comment = new Comment();
        comment.createComment(request.getContent(),findForum,writer);
        commentRepository.save(comment);
        return ResponseEntity.ok(new CommentResponseDto.Body(comment,null));
//                CommentResponseDto.success(comment,null);
//                new CommentResponseDto.createCommentResponse(comment.getId(),findForum.getId(),writer.getNickName(),request.getContent());
    }

    @Transactional
    public ResponseEntity<?> updateComment(Long comment_id, CommentRequestDto.updateCommentRequest request){
        Comment findComment = commentRepository.findById(comment_id).get();
        if(findComment == null){
            throw new CommentNotFoundException("아이디가 " + comment_id + "인 댓글은 존재하지 않습니다.");
        }
        findComment.updateComment(request.getContent());
        return ResponseEntity.ok(new CommentResponseDto.Body(findComment,
                findComment.getNestedComments().stream().map(
                        nested_comment -> new NestedCommentResponseDto.Body(nested_comment
                        )).collect(Collectors.toList())));
    }

    @Transactional
    public String deleteComment(Long forum_id,Long comment_id){
        Forum findForum = forumService.findOne(forum_id);
        if(findForum == null){
            throw new ForumNotFoundException("아이디가 " + forum_id + "인 forum은 존재하지 않습니다.");
        }
        Comment findComment = commentRepository.findById(comment_id).get();
        if(findComment == null){
            throw new CommentNotFoundException("아이디가 " + comment_id + "인 댓글은 존재하지 않습니다.");
        }
        findComment.deleteComment();
        return "comment_id " +comment_id +  " 삭제 성공";
    }

    public ResponseEntity<?> getCommentByCommentId(Long forum_id,Long comment_id) {
        Forum findForum = forumService.findOne(forum_id);
        if(findForum == null){
            throw new ForumNotFoundException("아이디가 " + forum_id + "인 forum은 존재하지 않습니다.");
        }
        Comment checkComment = commentRepository.findById(comment_id).get();
        if(checkComment == null){
            throw new CommentNotFoundException("아이디가 " + comment_id + "인 댓글은 존재하지 않습니다.");
        }
        Comment findComment = commentRepository.findCommentWithMemberForumByCommentId(comment_id);
        CommentResponseDto.Body commentDataToGetResult =
                new CommentResponseDto.Body(
                        findComment,
                        findComment.getNestedComments().stream().map(
                                nested_comment -> new NestedCommentResponseDto.Body(nested_comment
                                )).collect(Collectors.toList()));
        return ResponseEntity.ok(new GetResult(1,commentDataToGetResult));
    }

    public ResponseEntity<?> getAllByForumId(Long forum_id) {
        Forum findForum = forumService.findOne(forum_id);
        if(findForum == null){
            throw new ForumNotFoundException("아이디가 " + forum_id + "인 forum은 존재하지 않습니다.");
        }
        List<Comment> comments = commentRepository.findCommentWithMemberForumByForumId(forum_id);
        List<CommentResponseDto.Body> commentDataToGetResults = comments.stream().map(
                        comment -> new CommentResponseDto.Body(comment,
                                comment.getNestedComments().stream().map(
                                        nested_comment -> new NestedCommentResponseDto.Body(nested_comment))
                                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new GetResult(commentDataToGetResults.size(),commentDataToGetResults));

    }
}
