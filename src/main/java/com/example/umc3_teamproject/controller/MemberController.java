package com.example.umc3_teamproject.controller;


import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.dto.*;
import com.example.umc3_teamproject.service.JwtService;
import com.example.umc3_teamproject.service.LoginService;

import com.example.umc3_teamproject.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired  // 객체 생성을 스프링에서 자동으로 생성해주는 역할. 주입하려 하는 객체의 타입이 일치하는 객체를 자동으로 주입한다.
    // IoC(Inversion of Control, 제어의 역전) / DI(Dependency Injection, 의존관계 주입)에 대한 공부하시면, 더 깊이 있게 Spring에 대한 공부를 하실 수 있을 겁니다!(일단은 모르고 넘어가셔도 무방합니다.)
    // IoC 간단설명,  메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부에서 결정되는 것을 의미
    // DI 간단설명, 객체를 직접 생성하는 게 아니라 외부에서 생성한 후 주입 시켜주는 방식
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
        //  @RequestBody란, 클라이언트가 전송하는 HTTP Request Body(우리는 JSON으로 통신하니, 이 경우 body는 JSON)를 자바 객체로 매핑시켜주는 어노테이션
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        // email에 값이 존재하는지, 빈 값으로 요청하지는 않았는지 검사합니다. 빈값으로 요청했다면 에러 메시지를 보냅니다.
        if (signupReq.getEmail() == null) {
            return new ResponseTemplate<>(EMPTY_EMAIL);
        }
        //이메일 정규표현: 입력받은 이메일이 email@domain.xxx와 같은 형식인지 검사합니다. 형식이 올바르지 않다면 에러 메시지를 보냅니다.
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
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            LoginRes loginRes = loginService.logIn(loginReq);
            return new ResponseTemplate<>(loginRes);
        } catch (ResponseException exception) {
            return new ResponseTemplate<>(FAIL);
        }
    }



    /**
     * 모든 회원들의  조회 API
     * [GET] /members
     *
     * 또는
     *
     * 해당 닉네임을 같는 유저들의 정보 조회 API
     * [GET] /members?nickName=
     */

    //Query String
    @ResponseBody   // return되는 자바 객체를 JSON으로 바꿔서 HTTP body에 담는 어노테이션.
    //  JSON은 HTTP 통신 시, 데이터를 주고받을 때 많이 쓰이는 데이터 포맷.
    @GetMapping("") // (GET) 127.0.0.1:9000/users
    // GET 방식의 요청을 매핑하기 위한 어노테이션
    public ResponseTemplate<List<MemberRes>> getUsers(@RequestParam(required = false) String nickname) {
        //  @RequestParam은, 1개의 HTTP Request 파라미터를 받을 수 있는 어노테이션(?뒤의 값). default로 RequestParam은 반드시 값이 존재해야 하도록 설정되어 있지만, (전송 안되면 400 Error 유발)
        //  지금 예시와 같이 required 설정으로 필수 값에서 제외 시킬 수 있음
        //  defaultValue를 통해, 기본값(파라미터가 없는 경우, 해당 파라미터의 기본값 설정)을 지정할 수 있음
        try {
            if (nickname == null) { // query string인 nickname이 없을 경우, 그냥 전체 유저정보를 불러온다.
                List<MemberRes> getUsersRes = loginService.getUsers();
                return new ResponseTemplate<>(getUsersRes);
            }
            // query string인 nickname이 있을 경우, 조건을 만족하는 유저정보들을 불러온다.
            List<MemberRes> getUsersRes = loginService.getUsersByNickname(nickname);
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
    @GetMapping("/{memberId}") // (GET) 127.0.0.1:9000/users/:userIdx
    public ResponseTemplate<Member> getUser(@PathVariable("memberId") Long memberId) {
        // @PathVariable RESTful(URL)에서 명시된 파라미터({})를 받는 어노테이션, 이 경우 userId값을 받아옴.
        //  null값 or 공백값이 들어가는 경우는 적용하지 말 것
        //  .(dot)이 포함된 경우, .을 포함한 그 뒤가 잘려서 들어감
        // Get Users
        try {
            Member getUserRes = loginService.getUser(memberId);
            return new ResponseTemplate<>(getUserRes);
        } catch (ResponseException exception) {
            return new ResponseTemplate<>((exception.getStatus()));
        }

    }

    /**
     * 유저정보변경 API
     * [PATCH] /members/:memberId
     */
    @ResponseBody
    @PatchMapping("/{memberId}")
    public ResponseTemplate<String> modifyUserName(@PathVariable("memberId") Long memberId, @RequestBody Member member) {
        try {

            //*********** 해당 부분은 7주차 - JWT 수업 후 주석해체 해주세요!  ****************
            //jwt에서 idx 추출.
            Long userIdxByJwt = jwtService.getmemberId();
            //userIdx와 접근한 유저가 같은지 확인
            if(memberId != userIdxByJwt){
                return new ResponseTemplate<>(NO_JWT);
            }
            //같다면 유저네임 변경
//  **************************************************************************
            UpdateNickNameReq updateNickNameReq = new UpdateNickNameReq(memberId, member.getNickName());
            memberService.modifyNickName(updateNickNameReq);

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
    @DeleteMapping("/{memberId}")
    public ResponseTemplate<String> DeleteUser(@PathVariable("memberId") Long userIdx) {
        try {
            Long userIdxByJwt = jwtService.getmemberId();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new ResponseTemplate<>(NO_JWT);
            }

            DeleteUserReq deleteUserReq = new DeleteUserReq(userIdx);
            memberService.deleteUser(deleteUserReq);

            String result = userIdx + "의 정보가 완전히 삭제되었습니다.";
            return new ResponseTemplate<>(result);
        } catch (ResponseException exception) {
            return new ResponseTemplate<>((exception.getStatus()));
        }
    }

}
