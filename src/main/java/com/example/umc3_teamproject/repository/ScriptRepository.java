package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Script;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
// @RequiredArgsConstructor   // @AllArgsConstructor 대신 사용
public interface ScriptRepository extends JpaRepository<Script, Long> {
    Optional<Script> findById(Long id);
}
