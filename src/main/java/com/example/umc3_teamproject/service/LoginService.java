package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus;
import com.example.umc3_teamproject.dto.LoginReq;
import com.example.umc3_teamproject.dto.LoginRes;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class LoginService {


    public ResponseTemplate<LoginRes> logIn(@RequestBody @Valid LoginReq loginReq) throws ResponseException {
        if(loginReq.getEmail() == null) return new ResponseTemplate<LoginRes>(ResponseTemplateStatus.EMPTY_EMAIL);

    }
}
