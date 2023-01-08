package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.ForumImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ForumImageRepository extends JpaRepository<ForumImage,Long> {
    @Transactional
    @Modifying
    @Query("delete from ForumImage f where f.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
