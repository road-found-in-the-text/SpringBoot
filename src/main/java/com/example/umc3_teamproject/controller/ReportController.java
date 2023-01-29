package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.request.MailRequestDto;
import com.example.umc3_teamproject.domain.dto.request.ReportRequestDto;
import com.example.umc3_teamproject.service.MailService;
import com.example.umc3_teamproject.service.ReportService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = {"신고 Api"})
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    private final MailService mailService;

    @ApiOperation(value = "forum 생성", notes = "게시글을 create한다.")
    @PostMapping("/new")
    public ResponseTemplate<String> createReport(@RequestBody @Valid ReportRequestDto.createReport request) {
        String address = "road.found.in.the.text@gmail.com";
        String s = mailService.reportMailSend(new MailRequestDto.MailDto(address, request.getTitle(), request.getContent()));
        ResponseTemplate<String> report = reportService.createReport(s, request);
        return report;
    }


    @PostMapping("/emailConfirm")
    @ApiOperation(value = "회원 가입시 이메인 인증", notes = "기존 사용하고 있는 이메일을 통해 인증")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> emailConfirm(
            @RequestBody @ApiParam(value="이메일정보 정보", required = true) MailRequestDto.emailConfirmDto email) throws Exception {
        String confirm = mailService.sendSimpleMessage(email.getEmail());
        return ResponseEntity.status(200).body(new ResponseTemplate("메일 보내기 성공\n"+"인증번호 : " + confirm));
    }

}
