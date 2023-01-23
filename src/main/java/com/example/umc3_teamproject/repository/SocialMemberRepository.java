package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.item.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialMemberRepository extends JpaRepository<Member,String> {
    Optional<Member> findBySocialId(String socialId);
}
