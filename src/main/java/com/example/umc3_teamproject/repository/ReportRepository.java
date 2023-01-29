package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}
