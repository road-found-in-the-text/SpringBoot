package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.ForumScript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ForumScriptRepository extends JpaRepository<ForumScript,Long> {

    @Override
    void deleteAll();

    @Transactional
    @Modifying
    @Query("delete from ForumScript f where f.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
