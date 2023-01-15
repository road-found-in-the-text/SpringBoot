package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Script;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// @RequiredArgsConstructor   // @AllArgsConstructor 대신 사용
public interface ScriptRepository extends JpaRepository<Script, Long> , CrudRepository<Script, Long> {
    Optional<Script> findById(Long id);

    // Optional<Script> findByUserId(Long id);

    List<Script> findByUserId(Long id);

    // @SQLDelete(sql = "UPDATE script SET deleted = true WHERE scriptId = ?")
    // Optional<Script> deleteScriptById(Long id);
}
