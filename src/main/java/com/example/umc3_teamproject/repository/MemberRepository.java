package com.example.umc3_teamproject.repository;


import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;;
import java.util.List;



//데이터베이스 관련 작업을 전담.
//데이터베이스에 연결하여 입력/수정/삭제/조회 등 작업 수행

@Repository
public class MemberRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Transactional
    public Long createUser(SignupReq signupReq) {
        String createUserQuery = "insert into Member (email, pw,  nick_name, tier, image_url, login_type, comments_alarm_permission, voice_permission, event_permission, report_status) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)"; // 실행될 동적 쿼리문

        Object[] createUserParams = new Object[]{signupReq.getEmail(),signupReq.getPw(),signupReq.getNickName(), signupReq.getTier(), signupReq.getImageUrl(), 0, false, false, false, false}; // 동적 쿼리의 ?부분에 주입될 값

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        // email -> postUserReq.getEmail(), password -> postUserReq.getPassword(), nickname -> postUserReq.getNickname() 로 매핑(대응)시킨다음 쿼리문을 실행한다.
        // 즉 DB의 User Table에 (email, password, nickname)값을 가지는 유저 데이터를 삽입(생성)한다.

        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, Long.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }

    // 이메일 확인
    public Long checkEmail(String email) {
        String checkEmailQuery = "select exists(select email from Member where email = ?)"; // User Table에 해당 email 값을 갖는 유저 정보가 존재하는가?
        String checkEmailParams = email; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkEmailQuery, Long.class, checkEmailParams); // checkEmailQuery, checkEmailParams를 통해 가져온 값(intgud)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    // 회원정보 변경
    public int modifyUserName(UpdateNickNameReq updateNickNameReq) {
        String modifyUserNameQuery = "update Member set nick_name = ? where member_id = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyUserNameParams = new Object[]{updateNickNameReq.getNickName(), updateNickNameReq.getMemberId()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    @Transactional(readOnly = true)
    // 로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
    public Member getPw(LoginReq loginReq) {
        String getPwQuery = "select *  from Member where email = ?"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        String getPwParams = loginReq.getEmail(); // 주입될 email값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.
        return this.jdbcTemplate.queryForObject(getPwQuery,
                (rs, rowNum) -> new Member(
                        rs.getString("email"),
                        rs.getString("pw"),
                        rs.getString("nick_name"),
                        rs.getString("image_url"),
                        rs.getInt("tier"),
                        rs.getInt("login_type"),
                        rs.getBoolean("comments_alarm_permission"),
                        rs.getBoolean("voice_permission"),
                        rs.getBoolean("event_permission"),
                        rs.getBoolean("report_status"))
                , // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getPwParams
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    // 해당 nickname을 갖는 유저들의 정보 조회
    @Transactional(readOnly = true)
    public List<MemberRes> getUsersByNickName(String name) {
        String getUsersByNickNameQuery = "select * from Member where nick_name =?"; // 해당 이메일을 만족하는 유저를 조회하는 쿼리문
        String getUsersByNickNameParams = name;
        return this.jdbcTemplate.query(getUsersByNickNameQuery,
                (rs, rowNum) -> new MemberRes(
                        rs.getLong("member_id"),
                        rs.getString("nick_name"),
                        rs.getString("image_url"),
                        rs.getInt("tier"),
                        rs.getInt("login_type"),
                        rs.getBoolean("comments_alarm_permission"),
                        rs.getBoolean("voice_permission"),
                        rs.getBoolean("event_permission"),
                        rs.getBoolean("report_status")
                        ),

                         // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getUsersByNickNameParams); // 해당 닉네임을 갖는 모든 User 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }


    @Transactional(readOnly = true)
    public List<MemberRes> getUsers() {
        String getUsersQuery = "select * from Member"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new MemberRes(
                        rs.getLong("member_id"),
                        rs.getString("nick_name"),
                        rs.getString("image_url"),
                        rs.getInt("tier"),
                        rs.getInt("login_type"),
                        rs.getBoolean("comments_alarm_permission"),
                        rs.getBoolean("voice_permission"),
                        rs.getBoolean("event_permission"),
                        rs.getBoolean("report_status")
                ))
                        ; // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
         // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)
    }

    // 해당 userIdx를 갖는 유저조회
    public Member getUser(Long userIdx) {
        String getUserQuery = "select * from Member where member_id = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        Long getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new Member(
                        rs.getString("email"),
                        rs.getString("pw"),
                        rs.getString("nick_name"),
                        rs.getString("image_url"),
                        rs.getInt("tier"),
                        rs.getInt("login_type"),
                        rs.getBoolean("comments_alarm_permission"),
                        rs.getBoolean("voice_permission"),
                        rs.getBoolean("event_permission"),
                        rs.getBoolean("report_status")
                ),
                getUserParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    //USER table tuple 삭제
    @Transactional
    public int deleteUser(DeleteUserReq deleteUserReq) {
        String deleteUserQuery = "delete from Member where member_id = ?"; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] deleteUserParams = new Object[]{deleteUserReq.getUserIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(deleteUserQuery, deleteUserParams);
    }



}
