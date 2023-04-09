package com.example.umc3_teamproject.controller;


import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.dto.*;
import com.example.umc3_teamproject.service.JwtService;
import com.example.umc3_teamproject.service.LoginService;

import com.example.umc3_teamproject.service.MemberService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus.*;

@RestController
@Api(tags = {"Member Api"})
@RequestMapping("/members")
public class MemberController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private final MemberService memberService;
    @Autowired
    private final LoginService loginService;
    @Autowired
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    public MemberController(MemberService memberService, LoginService loginService, JwtService jwtService) {
        this.memberService = memberService;
        this.loginService = loginService;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }

    // ******************************************************************************

    /**
     * 회원가입 API
     * [POST] /members/signup
     */
    // Body
    @ResponseBody
    @PostMapping("/signup")    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public ResponseTemplate<SignupRes> createUser(@RequestBody SignupReq signupReq) {
        if (signupReq.getEmail() == null) {
            return new ResponseTemplate<>(EMPTY_EMAIL);
        }
        if (signupReq.getPw()==null){
            return new ResponseTemplate<>(EMPTY_PASSWORD);
        }
        if (!isRegexEmail(signupReq.getEmail())) {
            return new ResponseTemplate<>(INVALID_EMAIL);
        }
        try {
            SignupRes signupRes = memberService.createUser(signupReq);
            return new ResponseTemplate<>(signupRes);
        } catch (ResponseException exception) {
            return new ResponseTemplate<>((exception.getStatus()));
        }
    }

    /**
     * 로그인 API
     * [POST] /members/login
     */
    @ResponseBody
    @PostMapping("/login")
    public ResponseTemplate<LoginRes> logIn(@RequestBody LoginReq loginReq) {
        try {
            LoginRes loginRes = loginService.logIn(loginReq);
            return new ResponseTemplate<>(loginRes);
        } catch (ResponseException exception) {
            return new ResponseTemplate<>(FAIL);
        }
    }


    //Query String
    @ResponseBody   // return되는 자바 객체를 JSON으로 바꿔서 HTTP body에 담는 어노테이션.
    @GetMapping("")
    public ResponseTemplate<List<Member>> getUsers(@RequestParam(required = false) String nickname) {
        try {
            if (nickname == null) {
                List<Member> getUsersRes = loginService.getUsers();
                return new ResponseTemplate<>(getUsersRes);
            }
            List<Member> getUsersRes = loginService.getUsersByNickname(nickname);
            return new ResponseTemplate<>(getUsersRes);
        } catch (ResponseException exception) {
            return new ResponseTemplate<>((exception.getStatus()));
        }
    }




    /**
     * 회원 1명 조회 API
     * [GET] /members/:memberId
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{memberId}")
    public ResponseTemplate<Member> getUser(@PathVariable("memberId") Long memberId) {

        try {
            Member getUserRes = loginService.getUser(memberId);
            return new ResponseTemplate<>(getUserRes);
        } catch (ResponseException exception) {
            return new ResponseTemplate<>((exception.getStatus()));
        }

    }

    /**
     * 유저 닉네임변경 API
     * [PATCH] /members/:memberId
     */
    @ResponseBody
    @PatchMapping("/memberName")
    public ResponseTemplate<String> modifyUserName(@RequestBody UpdateNickNameReq updateNickNameReq) {
        try {
            //jwt에서 idx 추출.
            Long userIdxByJwt = jwtService.getmemberId();
            //userIdx와 접근한 유저가 같은지 확인

            if (!loginService.getUsersByNickname(updateNickNameReq.getNickName()).isEmpty()){
                return new ResponseTemplate<>(NICKNAME_DUPLICATED);
            }
            memberService.modifyNickName(updateNickNameReq);

            String result = "회원정보가 수정되었습니다.";
            return new ResponseTemplate<>(result);
        } catch (ResponseException exception) {
            return new ResponseTemplate<>((exception.getStatus()));
        }
    }


    /**
     * 유저 한줄소개 변경 API
     * [PATCH] /members/introduction
     */
    @ResponseBody
    @PatchMapping("/members/introduction")
    public ResponseTemplate<String> modifyIntroduction(@RequestBody UpdateIntroReq updateIntroReq) {
        try {
            //jwt에서 idx 추출.
            Long userIdxByJwt = jwtService.getmemberId();
            //userIdx와 접근한 유저가 같은지 확인

            memberService.modifyIntroduction(updateIntroReq);

            String result = "회원정보가 수정되었습니다.";
            return new ResponseTemplate<>(result);
        } catch (ResponseException exception) {
            return new ResponseTemplate<>((exception.getStatus()));
        }
    }


    // 이메일 형식 체크
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }



    @ResponseBody
    @DeleteMapping("/")
    public ResponseTemplate<String> DeleteUser() {
        try {
            Long userIdx = jwtService.getmemberId();

            DeleteUserReq deleteUserReq = new DeleteUserReq(userIdx);
            memberService.deleteUser(deleteUserReq);

            String result = userIdx + "의 정보가 완전히 삭제되었습니다.";
            return new ResponseTemplate<>(result);
        } catch (ResponseException exception) {
            return new ResponseTemplate<>((exception.getStatus()));
        }
    }
}
