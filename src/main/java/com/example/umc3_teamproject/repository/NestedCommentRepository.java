package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.NestedComment;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface NestedCommentRepository extends JpaRepository<NestedComment,Long> {

    public List<NestedComment> findByCommentId(Long comment_id);

    @Query("select n from NestedComment n join fetch n.comment join fetch n.member where n.comment.id = :comment_id")
    List<NestedComment> findNestedCommentWithMemberCommentByCommentId(@Param("comment_id") Long comment_id);

    @Query("select n from NestedComment n join fetch n.comment join fetch n.member where n.id = :nested_comment_id")
    NestedComment findNestedCommentWithMemberCommentByNestedCommentId(@Param("nested_comment_id") Long nested_comment_id);

}
