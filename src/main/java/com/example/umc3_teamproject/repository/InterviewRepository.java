package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    Optional<Interview> findById(Long id);

    List<Interview> findByUserId(Long id);
}
