package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);

    boolean existsMemberByEmail(String email);

}