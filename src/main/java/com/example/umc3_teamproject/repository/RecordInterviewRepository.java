package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.RecordInterview;
import com.example.umc3_teamproject.domain.item.RecordScript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecordInterviewRepository  extends JpaRepository<RecordInterview, Long>, CrudRepository<RecordInterview, Long> {
    Optional<RecordInterview> findById(Long id);
}
