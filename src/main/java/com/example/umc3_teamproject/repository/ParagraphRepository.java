package com.example.umc3_teamproject.repository;
import com.example.umc3_teamproject.domain.item.Paragraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParagraphRepository extends JpaRepository<Paragraph, Long> , CrudRepository<Paragraph, Long> {
    Optional<Paragraph> findById(Long id);
    //List<Paragraph> findByUserId(Long id);


}