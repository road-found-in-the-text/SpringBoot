/*
package com.example.umc3_teamproject.repository;


import com.example.umc3_teamproject.domain.item.Paragraph;
import com.example.umc3_teamproject.domain.item.ParagraphImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ParagraphImageRepository extends JpaRepository<ParagraphImage,Long> {
    @Transactional
    @Modifying
    @Query("delete from ParagraphImage f where f.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

    @Transactional
    @Modifying
    @Query("delete from ParagraphImage f where f.id = :id")
    void deleteByIdInQuery(@Param("id") Long id);

    List<ParagraphImage> findAllByParagraphId(Long paragraphId);


}

 */


