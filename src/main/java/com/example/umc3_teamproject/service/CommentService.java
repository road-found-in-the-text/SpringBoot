package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.NestedCommentData;
import com.example.umc3_teamproject.domain.dto.request.createCommentRequest;
import com.example.umc3_teamproject.domain.dto.request.updateCommentRequest;
import com.example.umc3_teamproject.domain.dto.response.CommentDataToGetResult;
import com.example.umc3_teamproject.domain.dto.response.createCommentResponse;

import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.Forum;
import com.example.umc3_teamproject.dto.MemberRes;
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
    public createCommentResponse createComment (Long forum_id, createCommentRequest request){
        Forum findForum = forumService.findOne(forum_id);
        Member writer = memberService.findById(request.getUser_id());
        Comment comment = new Comment();
        comment.createComment(request.getContent(),findForum,writer);
        commentRepository.save(comment);
        return new createCommentResponse(comment.getId(),findForum.getId(),writer.getNickName(),request.getContent());
    }

    @Transactional
    public createCommentResponse updateComment(Long comment_id, updateCommentRequest request){
        Comment findComment = commentRepository.findById(comment_id).get();
        findComment.updateComment(request.getContent());
        return new createCommentResponse(findComment.getId(),findComment.getForum().getId(),findComment.getMember().getNickName(),
                findComment.getContent());
    }

    @Transactional
    public String deleteComment(Long comment_id){
        Comment findComment = commentRepository.findById(comment_id).get();
        findComment.deleteComment();
        return "comment_id " +comment_id +  " 삭제 성공";
    }

    public GetResult getCommentByCommentId(Long comment_id) {
        Comment findComment = commentRepository.findById(comment_id).get();
        CommentDataToGetResult commentDataToGetResult =
                new CommentDataToGetResult(
                        findComment.getMember().getId(),findComment.getForum().getId(),
                        findComment.getId(),findComment.getContent(),findComment.getLike_num(),
                        findComment.getNestedComments().stream().map(
                                nested_comment -> new NestedCommentData(nested_comment.getId(),nested_comment.getMember().getNickName(),
                                        nested_comment.getContent(),nested_comment.getLike_num()
                                )).collect(Collectors.toList()));
        return new GetResult(1,commentDataToGetResult);

    }

    public GetResult getAllByForumId(Long forum_id) {
        List<Comment> comments = commentRepository.findByForumId(forum_id).stream().map(
                comment -> comment
        ).collect(Collectors.toList());
        List<CommentDataToGetResult> commentDataToGetResults = comments.stream().map(
                        s -> new CommentDataToGetResult(s.getMember().getId(),s.getForum().getId(),
                                s.getId(),s.getContent(),s.getLike_num(),
                                s.getNestedComments().stream().map(
                                        nested_comment -> new NestedCommentData(nested_comment.getId(),nested_comment.getMember().getNickName(),
                                                nested_comment.getContent(),nested_comment.getLike_num()
                                )).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return new GetResult(commentDataToGetResults.size(),commentDataToGetResults);
    }
}
