package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Interview;
import com.example.umc3_teamproject.domain.item.InterviewParagraph;
import com.example.umc3_teamproject.domain.item.Memo;
import com.example.umc3_teamproject.domain.item.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    @Transactional
    @Modifying
    @Query("delete from Memo m where m.type = 'script' and m.script_interview_id = :id")
    void deleteByScriptIdInQuery(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("delete from Memo m where m.type = 'interview' and m.script_interview_id = :id")
    void deleteByInterviewIdInQuery(@Param("id") Long id);
//
//    @Query("select m from Memo m join fetch m.interview where m.interview.interviewId = :interview_id ")
//    List<InterviewParagraph> findByIntervieId(@Param("interview_id") Long interview_id);
//
//    @Query("select m from Memo m join fetch m.script where m.script.scriptId = :script_id ")
//    List<InterviewParagraph> findByScriptId(@Param("script_id") Long script_id);

    @Query("select m from Memo m where m.type = 'script' and m.script_interview_id = :id order by m.result_count asc ")
    List<Memo> findByScriptId(@Param("id") Long id);

    @Query("select m from Memo m where m.type = 'script' and m.script_interview_id = :id order by m.result_count asc ")
    List<Memo> findByInterviewId(@Param("id") Long id);
}
