package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.domain.dto.request.EmailRequestDto;
import com.example.umc3_teamproject.dto.MailDto;
import com.example.umc3_teamproject.mail2.EmailService;
import com.example.umc3_teamproject.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// https://1-7171771.tistory.com/85

@RestController
public class MailController {
    @Autowired
    SendEmailService sendEmailService;


    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
    @PostMapping("/check/findPw/sendEmail")
    public String sendEmail(@RequestBody EmailRequestDto.Register emailRequestDto) throws ResponseException {
        MailDto sentMail= sendEmailService.createMailAndChangePassword(emailRequestDto.getEmail());

        return "done";

    }
}
