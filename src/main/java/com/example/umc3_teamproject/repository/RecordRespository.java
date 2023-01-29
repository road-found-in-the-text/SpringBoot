package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRespository extends JpaRepository<Record, Long> {

}