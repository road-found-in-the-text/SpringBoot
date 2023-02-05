package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Interview;
import com.example.umc3_teamproject.domain.item.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    @Transactional
    @Modifying
    @Query("delete from Memo m where m.script.scriptId in :id")
    void deleteByScriptIdInQuery(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("delete from Memo m where m.interview.interviewId in :id")
    void deleteByInterviewIdInQuery(@Param("id") Long id);
}
