package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.NestedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NestedCommentRepository extends JpaRepository<NestedComment,Long> {

    public List<NestedComment> findByCommentId(Long comment_id);

}
