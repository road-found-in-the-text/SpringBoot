package com.example.umc3_teamproject.repository;


import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.domain.LoginType;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.Tier;
import com.example.umc3_teamproject.domain.dto.response.ForumResponseDto;
import com.example.umc3_teamproject.domain.item.Forum;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.dto.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;;
import java.util.List;
import java.util.Optional;

import static com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus.*;


//데이터베이스 관련 작업을 전담.
//데이터베이스에 연결하여 입력/수정/삭제/조회 등 작업 수행


@Repository @RequiredArgsConstructor
public class MemberRepository {


    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }




    @Transactional(rollbackFor = Exception.class)
    public Long createMember(SignupReq signupReq) {
        Member member = new Member();
        member.createMember(signupReq.getEmail(),signupReq.getPw(), signupReq.getNickName(), signupReq.getImageUrl());
        if (member.getId() == null){
            em.persist(member);
        }
        else {
            em.merge(member);
        }
        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값을 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }




    // 이메일 확인
    public Long checkEmail(String email) {
        try {
            String checkEmailQuery = "select exists(select email from umc3.member where email = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
            String checkEmailParams = email; // 해당(확인할) 이메일 값
            return this.jdbcTemplate.queryForObject(checkEmailQuery, Long.class, checkEmailParams); // checkEmailQuery, checkEmailParams를 통해 가져온 값(intgud)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
        } catch (EmptyResultDataAccessException e){
            return Long.valueOf(0);
        }
    }

    // 회원정보 변경
    public int modifyUserName(UpdateNickNameReq updateNickNameReq) {
        String modifyUserNameQuery = "update umc3.member set nick_name = ? where member_id = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyUserNameParams = new Object[]{updateNickNameReq.getNickName(), updateNickNameReq.getMemberId()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    @Transactional(readOnly = true)
    // 로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
    public Member getPw(LoginReq loginReq) throws ResponseException {
        TypedQuery<Member> getPwQuery = em.createQuery("select m from Member m where m.email = :email", Member.class); // 해당 email을 만족하는 User의 정보들을 조회한다.
        getPwQuery.setParameter("email",loginReq.getEmail());
        Member member = getPwQuery.getSingleResult();
        return member;
    }


    // 해당 nickname을 갖는 유저들의 정보 조회
    @Transactional(readOnly = true)
    public List<MemberRes> getUsersByNickName(String name) {
        String getUsersByNickNameQuery = "select * from umc3.member where nickName =?"; // 해당 이메일을 만족하는 유저를 조회하는 쿼리문
        String getUsersByNickNameParams = name;
        return this.jdbcTemplate.query(getUsersByNickNameQuery,
                (rs, rowNum) -> new MemberRes(
                        rs.getLong("member_id"),
                        rs.getString("social_id"),
                        rs.getString("introduction"),
                        rs.getString("email"),
                        rs.getString("nick_name"),
                        rs.getString("image_url"),
                        rs.getInt("tier"),
                        rs.getInt("login_type"),
                        rs.getInt("member_status"),
                        rs.getInt("block_status")
                        ),

                         // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUsersByNickNameParams); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }




    @Transactional(readOnly = true)
    public List<MemberRes> getUsers() {
        String getUsersQuery = "select * from umc3.member"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new MemberRes(
                        rs.getLong("member_id"),
                        rs.getString("social_id"),
                        rs.getString("introduction"),
                        rs.getString("email"),
                        rs.getString("nick_name"),
                        rs.getString("image_url"),
                        rs.getInt("tier"),
                        rs.getInt("login_type"),
                        rs.getInt("member_status"),
                        rs.getInt("block_status")
                ))
                        ; // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
         // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)
    }

    // 해당 userIdx를 갖는 유저조회
    public Member getUser(Long userIdx) {
        String getUserQuery = "select * from umc3.member where member_id = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        Long getUserParams = userIdx;

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new Member(
                        rs.getLong("member_id"),
                        rs.getString("email"),
                        rs.getString("pw"),
                        rs.getString("introduction"),
                        rs.getString("social_id"),
                        rs.getString("nick_name"),
                        rs.getString("image_url"),
                        Tier.values()[rs.getInt("tier")],
                        LoginType.values()[rs.getInt("login_type")],
                        rs.getInt("member_status"),
                        rs.getInt("block_status")
                ),
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }


    //USER table tuple 삭제
    @Transactional
    public int deleteUser(DeleteUserReq deleteUserReq) {

        String deleteUserQuery = "delete from umc3.member where member_id = ?"; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] deleteUserParams = new Object[]{deleteUserReq.getUserIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(deleteUserQuery, deleteUserParams);
    }

    // 이메일로 user id 찾기
    @Transactional
    public Optional<Long> findUserIdByEmail(String email) {
        String findUserQuery = "select member_id from umc3.member where email = ?"; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        // Object[] deleteUserParams = new Object[]{deleteUserReq.getUserIdx()}; // 주입될 값들(nickname, userIdx) 순

        // return this.jdbcTemplate.query(findUserQuery, email);
        // 해당 email의 사용자를 못 찾을 경우 예외 처리
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(findUserQuery, Long.class, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void updatePassword(Long id, String pwd ) {

        String modifyPasswordQuery = "update umc3.member set pw = ? where member_id = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyPasswordParams = new Object[]{pwd, id}; // 주입될 값들(nickname, userIdx) 순

        jdbcTemplate.update(modifyPasswordQuery, modifyPasswordParams);
    }

    public int getScriptsNum(Long userIdx)  {
        int result=0;
        String queryString = "SELECT COUNT(*) " +
                "FROM Member m " +
                "LEFT JOIN Script s ON m.id=s.memberId ";


        if (userIdx!= null) {
            queryString+= "WHERE m.id= :userIdx" ;
        }
        TypedQuery<Long> query = em.createQuery(queryString, Long.class);
        query.setParameter("userIdx",userIdx);


        try {
            result = query.getSingleResult().intValue();
        } catch (NoResultException e) {
            result = 0;
        }
        System.out.println(result);

        return result;
    }


    // 회원 한줄소개 변경
    public int modifyIntroduction(UpdateIntroReq updateIntroReq) {
        String modifyUserNameQuery = "update umc3.member set introduction = ? where member_id = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyUserNameParams = new Object[]{updateIntroReq.getIntroduction(), updateIntroReq.getMemberId()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }
}


