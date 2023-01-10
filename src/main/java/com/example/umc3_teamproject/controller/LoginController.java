package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.dto.LoginReq;
import com.example.umc3_teamproject.dto.LoginRes;
import com.example.umc3_teamproject.service.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    //로그인
    @ResponseBody
    @PostMapping("/login")
    public ResponseTemplate<LoginRes> logIn(@RequestBody LoginReq loginReq){
        try {
            LoginRes loginRes = LoginService.logIn(loginReq);
            return new ResponseTemplate<>(loginRes);
        } catch (ResponseException exception){
            return new ResponseTemplate<>(exception.getStatus());
        }

    }


}
