package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.InterviewParagraph;
import com.example.umc3_teamproject.domain.item.InterviewParagraphResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterviewParagraphRepository extends JpaRepository<InterviewParagraph,Long> {

    @Query("select i from InterviewParagraph i join fetch i.interview where i.interview.interviewId = :interview_id ")
    List<InterviewParagraph> findByIntervieId(@Param("interview_id") Long interview_id);
}
