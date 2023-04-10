package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    boolean existsMemberByEmail(String email);
    List<Member> findByNickName(String nickName);
    List<Member> findAll();
    Optional<Member> findById(Long memberId);
    Member save(Member member);

    @Query("update Member m set m.nickName = :nickName where m.id = :id")
    int updateNickNameById(Long memberId, String nickName);

}