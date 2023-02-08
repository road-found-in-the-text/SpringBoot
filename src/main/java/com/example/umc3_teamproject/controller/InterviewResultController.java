package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
import com.example.umc3_teamproject.domain.dto.request.InterviewResultRequestDto;
import com.example.umc3_teamproject.domain.dto.request.InterviewResultRequestLocalDateTimeDto;
import com.example.umc3_teamproject.domain.dto.response.ForumResponseDto;
import com.example.umc3_teamproject.domain.dto.response.InterviewResultResponseDto;
import com.example.umc3_teamproject.service.InterviewParagraphResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"InterviewResult Api"})
public class InterviewResultController {

    private final InterviewParagraphResultService interviewParagraphResultService;

    @ApiOperation(value = "interview paragraph result 생성", notes = "질문 후 결과 create한다.")
    @PostMapping("/interviewparagraph/{paragraph-id}/result/new")
    public ResponseTemplate<String> createInterviewResult(@PathVariable("paragraph-id") Long paragraph_id
            , @ModelAttribute InterviewResultRequestDto.createInterviewResult request) throws IOException {
        interviewParagraphResultService.deleteInterviewParagraphResult(paragraph_id);
        return interviewParagraphResultService.createInterviewResult(paragraph_id,request);
    }

    @ApiOperation(value = "interview paragraph result 생성", notes = "질문 후 결과 create한다.")
    @GetMapping("/interview/{interview-id}/result")
    public ResponseTemplate<List<InterviewResultResponseDto.Body>> getInterviewResult(@PathVariable("interview-id") Long interview_id) throws IOException {
        return interviewParagraphResultService.getInterviewResult(interview_id);
    }
}
