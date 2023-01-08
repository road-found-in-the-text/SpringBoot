package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public List<Member> findAll() { //jpa query . sql(테이블)과 달리 user 객체에 대한 쿼리를 나타냄
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }



}
