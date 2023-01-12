package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.Forum;
import com.example.umc3_teamproject.domain.item.NestedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    public List<Comment> findByForumId(Long forum_id);

    @Query("select c from Comment c join fetch c.forum join fetch c.member where c.forum.id = :forum_id")
    List<Comment> findCommentWithMemberForumByForumId(@Param("forum_id") Long forum);

    @Query("select c from Comment c join fetch c.forum join fetch c.member where c.id = :comment_id")
    Comment findCommentWithMemberForumByCommentId(@Param("comment_id") Long comment_id);

    void deleteAllByForumId(Long forum_id);



}
