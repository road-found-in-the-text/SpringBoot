package com.example.umc3_teamproject.repository;


import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.dto.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;;
import java.util.List;
import java.util.Optional;

//데이터베이스 관련 작업을 전담.
//데이터베이스에 연결하여 입력/수정/삭제/조회 등 작업 수행


@Repository @RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;
    private final JpaMemberRepository jpaMemberRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long createMember(SignupReq signupReq) {
        Member member = new Member();
        member.createMember(signupReq.getEmail(),signupReq.getPw(), signupReq.getNickName(), signupReq.getImageUrl());
        if (member.getId() == null){
            em.persist(member);
        } else {
            em.merge(member);
        }
        return member.getId();
    }


    // 이메일 확인
    public boolean checkEmail(String email) {
            return jpaMemberRepository.existsMemberByEmail(email);
    }

    // 회원정보 변경
    public int modifyUserName(UpdateNickNameReq updateNickNameReq) {

        return em.createQuery("update Member m set m.nickName = :nickName where m.id = :memberId")
                .setParameter("nickName", updateNickNameReq.getNickName())
                .setParameter("memberId", updateNickNameReq.getMemberId())
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    // 로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
    public Member getPw(LoginReq loginReq) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", loginReq.getEmail())
                .getSingleResult();
    }


    // 해당 nickname을 갖는 유저들의 정보 조회
    @Transactional(readOnly = true)
    public List<Member> getUsersByNickName(String name) {
        return jpaMemberRepository.findByNickName(name);
    }

    @Transactional(readOnly = true)
    public List<Member> getUsers() {
        List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
        return members;
    }

    // 해당 userIdx를 갖는 유저조회
    public Member getUser(Long userIdx) {
        String getUserQuery = "select m from Member m where m.id = :userIdx";
        TypedQuery<Member> query = em.createQuery(getUserQuery, Member.class);
        query.setParameter("userIdx", userIdx);

        return query.getSingleResult();
    }


    //USER table tuple 삭제
    @Transactional
    public int deleteUser(DeleteUserReq deleteUserReq) {
        try {
            Member member = em.find(Member.class, deleteUserReq.getUserIdx());
            member.setMemberStatus(0);
            return 1;
        } catch (NoResultException e) {
            return 0;
        }
    }

    // 이메일로 user id 찾기
    @Transactional
    public Optional<Long> findUserIdByEmail(String email) {
        String findUserQuery = "select m.id from Member m where m.email = :email"; // 해당 email을 갖는 유저의 userIdx를 조회하는 쿼리문
        TypedQuery<Long> query = em.createQuery(findUserQuery, Long.class);
        query.setParameter("email", email);

        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void updatePassword(Long id, String pw ) {
        Member member = em.find(Member.class, id);
        member.setPw(pw);
    }

    public int getScriptsNum(Long userIdx)  {
        String queryString = "SELECT COUNT(s) FROM Script s WHERE s.memberId = :userIdx";
        TypedQuery<Long> query = em.createQuery(queryString, Long.class);
        query.setParameter("userIdx", userIdx);

        Long result = query.getSingleResult();
        return result != null ? result.intValue() : 0;
    }


    // 회원 한줄소개 변경
    public int modifyIntroduction(UpdateIntroReq updateIntroReq) {
        Member user = em.find(Member.class, updateIntroReq.getMemberId());
        user.setIntroduction(updateIntroReq.getIntroduction());

        return 1; // 변경 성공 시 1 반환
    }
}


