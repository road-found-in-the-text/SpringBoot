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
@RequestMapping("/interview/result")
public class InterviewResultController {

    private final InterviewParagraphResultService interviewParagraphResultService;

    @ApiOperation(value = "interview paragraph result 생성", notes = "질문 후 결과 create한다.")
    @PostMapping("/new/{paragraph-id}")
    public ResponseTemplate<String> createInterviewResult(@PathVariable("paragraph-id") Long paragraph_id
            , @ModelAttribute InterviewResultRequestDto.createInterviewResult request) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(request.getStartTime(),formatter);
        LocalDateTime endTime = LocalDateTime.parse(request.getEndTime(),formatter);
        InterviewResultRequestLocalDateTimeDto.createInterviewResult interviewResultRequestLocalDateTimeDto = new InterviewResultRequestLocalDateTimeDto.createInterviewResult(
                startTime,endTime,request.getVoiceFile(),request.getVoiceContent()
        );
        interviewParagraphResultService.deleteInterviewParagraphResult(paragraph_id);
        return interviewParagraphResultService.createInterviewResult(paragraph_id,interviewResultRequestLocalDateTimeDto);
    }

    @ApiOperation(value = "interview paragraph result 생성", notes = "질문 후 결과 create한다.")
    @GetMapping("/{interview-id}")
    public ResponseTemplate<List<InterviewResultResponseDto.Body>> getInterviewResult(@PathVariable("interview-id") Long interview_id) throws IOException {
        return interviewParagraphResultService.getInterviewResult(interview_id);
    }
}
