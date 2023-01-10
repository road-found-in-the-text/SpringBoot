package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    public List<Comment> findByForumId(Long forum_id);

}
