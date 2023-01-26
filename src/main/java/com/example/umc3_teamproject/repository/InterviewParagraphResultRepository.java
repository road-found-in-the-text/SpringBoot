package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.InterviewParagraphResult;
import com.example.umc3_teamproject.domain.item.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InterviewParagraphResultRepository extends JpaRepository<InterviewParagraphResult,Long>,
CrudRepository<InterviewParagraphResult,Long>{

    @Query("select i from InterviewParagraphResult i where i.interviewParagraph.Id = :interview_paragraph_id")
    InterviewParagraphResult findInterviewParagraphResultByInterviewParagraphId(@Param("interview_paragraph_id") Long interview_paragraph_id);

    @Query("select i from InterviewParagraphResult i join fetch i.interview where i.interview.interviewId = :interview_id order by i.interviewParagraph.paragraph_order asc ")
    List<InterviewParagraphResult> findByIntervieId(@Param("interview_id") Long interview_id);

    @Query("delete from InterviewParagraphResult i where i.interviewParagraph.Id = :interviewParagraph_id")
    void deleteByInterviewParagraph(@Param("interviewParagraph_id") Long interviewParagraph_id);
}
