package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.InterviewParagraph;
import com.example.umc3_teamproject.domain.item.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query("select r from Record r where r.type = 'script' and r.script_interview_id = :id order by r.result_count desc ")
    List<Record> findByScriptIdLimit1(@Param("id") Long id);

    @Query("select r from Record r where r.type = 'interview' and r.script_interview_id = :id order by r.result_count desc ")
    List<Record> findByIntervieIdLimit1(@Param("id") Long id);

    @Query(nativeQuery = true, value =
            "select * from record r where r.type = 'script' and r.script_interview_id = :id order by r.result_count desc limit 2")
    List<Record> findByScriptIdLimit2(@Param("id") Long id);

    @Query(nativeQuery = true, value =
            "elect * from record r where r.type = 'interview' and r.script_interview_id = :id order by r.result_count desc limit ")
    List<Record> findByIntervieIdLimit2(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("delete from Record r where r.type = 'script' and r.script_interview_id = :id")
    void deleteByScriptIdInQuery(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("delete from Record r where r.type = 'interview' and r.script_interview_id = :id")
    void deleteByInterviewIdInQuery(@Param("id") Long id);
}
