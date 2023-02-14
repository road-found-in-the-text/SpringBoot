package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.AES128;
import com.example.umc3_teamproject.config.SecurityConfig;
import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.dto.*;
import com.example.umc3_teamproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus.*;

@Service
public class MemberService {

    //final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final LoginService loginService;



    @Autowired //readme 참고
    public MemberService(MemberRepository memberRepository, LoginService loginService, JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.loginService = loginService;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    }
    // ******************************************************************************
    // 회원가입(POST)
    @Transactional(rollbackFor = Exception.class)
    public SignupRes createUser(SignupReq signupReq) throws ResponseException {
        // 중복 확인: 해당 이메일을 가진 유저가 있는지 확인합니다. 중복될 경우, 에러 메시지를 보냅니다.
        if (loginService.checkEmail(signupReq.getEmail()) == 1) {
            throw new ResponseException(EMAIL_DUPLICATED);
        }
        String pwd;
        try {
            pwd = new AES128(SecurityConfig.USER_INFO_PASSWORD_KEY).encrypt(signupReq.getPw()); // 암호화코드
            signupReq.setPw(pwd);
        } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new ResponseException(INVALID_JWT);
        }
        try {
            Long userIdx = memberRepository.createMember(signupReq);
//            return new PostUserRes(userIdx);
            String jwt = jwtService.createJwt(userIdx);
            return new SignupRes(userIdx, jwt);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new ResponseException(DATABASE_ERROR);
        }
    }

    public Member findById(Long userIdx) {
        return memberRepository.getUser(userIdx);
    }


    // 회원정보 수정(Patch)
    public void modifyNickName(UpdateNickNameReq updateNickNameReq) throws ResponseException {
        try {
            int result = memberRepository.modifyUserName(updateNickNameReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new ResponseException(MODIFY_FAIL_NICKNAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new ResponseException(PATCH_DATABASE_ERROR);
        }
    }

    //userIdx에 따른 하나의 회원 삭제, User table의 tuple 삭제
    public void deleteUser(DeleteUserReq deleteUserReq) throws ResponseException {
        try {
            int result = memberRepository.deleteUser(deleteUserReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new ResponseException(FAIL_WITHDRAW);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new ResponseException(DATABASE_ERROR);
        }
    }



    // 회원정보 수정(Patch)
    public void modifyIntroduction(UpdateIntroReq updateIntroReq) throws ResponseException {
        try {
            int result = memberRepository.modifyIntroduction(updateIntroReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new ResponseException(MODIFY_FAIL_INTRODUCTION);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new ResponseException(PATCH_DATABASE_ERROR);
        }
    }
}

