package com.example.umc3_teamproject.repository;
import com.example.umc3_teamproject.domain.item.RecordScript;
import com.example.umc3_teamproject.domain.item.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRespository extends JpaRepository<RecordScript, Long>, CrudRepository<RecordScript, Long> {
    Optional<RecordScript> findById(Long id);

}