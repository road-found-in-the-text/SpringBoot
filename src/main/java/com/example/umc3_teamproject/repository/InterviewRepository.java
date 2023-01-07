package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    Optional<Interview> findById(Long id);

    Optional<Interview> findByUserId(Long id);
}
