package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.dto.MailDto;
import com.example.umc3_teamproject.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// https://1-7171771.tistory.com/85

@RestController
public class MailController {
    @Autowired
    SendEmailService sendEmailService;

    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
    @PostMapping("/check/findPw/sendEmail")
    public String sendEmail(@RequestBody String userEmail, String userName) throws ResponseException {
        MailDto dto = sendEmailService.createMailAndChangePassword(userEmail, userName);
        System.out.println("dto: "+dto.getAddress());
        return dto.getAddress();

    }
}
