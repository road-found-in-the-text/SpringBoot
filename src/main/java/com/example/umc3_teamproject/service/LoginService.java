package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.AES128;
import com.example.umc3_teamproject.config.SecurityConfig;
import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.dto.LoginReq;
import com.example.umc3_teamproject.dto.LoginRes;
import com.example.umc3_teamproject.dto.MemberRes;
import com.example.umc3_teamproject.repository.MemberRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus.DATABASE_ERROR;
import static com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus.INVALID_PASSWORD;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

//    final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Autowired //readme 참고
    public LoginService(MemberRepository memberRepository, JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }
    // ******************************************************************************


    // 로그인(password 검사)
    public LoginRes logIn(LoginReq loginReq) throws ResponseException {
        Member member = memberRepository.getPw(loginReq);
        String password;
        try {
            password = new AES128(SecurityConfig.USER_INFO_PASSWORD_KEY).decrypt(member.getPw()); // 암호화
            // 회원가입할 때 비밀번호가 암호화되어 저장되었기 떄문에 로그인을 할때도 암호화된 값끼리 비교를 해야합니다.
        } catch (Exception ignored) {
            throw new ResponseException(INVALID_PASSWORD);
        }

        if (loginReq.getPw().equals(password)) { //비말번호가 일치한다면 userIdx를 가져온다.
            Long userIdx = memberRepository.getPw(loginReq).getId();
            // return new PostLoginRes(userIdx);
//  *********** 해당 부분은 7주차 - JWT 수업 후 주석해제 및 대체해주세요!  **************** //
            String jwt = jwtService.createJwt(userIdx);
            return new LoginRes(userIdx,jwt);
//  **************************************************************************

        } else { // 비밀번호가 다르다면 에러메세지를 출력한다.
            throw new ResponseException(INVALID_PASSWORD);
        }
    }

    // 해당 이메일이 이미 User Table에 존재하는지 확인
    public int checkEmail(String email) throws ResponseException {
        try {
            return memberRepository.checkEmail(email);
        } catch (Exception exception) {
            throw new ResponseException(DATABASE_ERROR);
        }
    }


    // User들의 정보를 조회
    public List<MemberRes> getUsers() throws ResponseException {
        try {
            List<MemberRes> getUserRes = memberRepository.getUsers();
            return getUserRes;
        } catch (Exception exception) {
            throw new ResponseException(DATABASE_ERROR);
        }
    }

    // 해당 nickname을 갖는 User들의 정보 조회
    public List<MemberRes> getUsersByNickname(String nickname) throws ResponseException {
        try {
            List<MemberRes> getUsersRes = memberRepository.getUsersByNickName(nickname);
            return getUsersRes;
        } catch (Exception exception) {
            throw new ResponseException(DATABASE_ERROR);
        }
    }


    // 해당 userIdx를 갖는 User의 정보 조회
    public MemberRes getUser(int userIdx) throws ResponseException {
        try {
            MemberRes getUserRes = memberRepository.getUser(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            throw new ResponseException(DATABASE_ERROR);
        }
    }
}
