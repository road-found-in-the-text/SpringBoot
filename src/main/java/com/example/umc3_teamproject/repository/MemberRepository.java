package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(Long id);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickName(String nickName);

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickName);


}
