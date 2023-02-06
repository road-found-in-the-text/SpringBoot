package com.example.umc3_teamproject.controller;


import com.example.umc3_teamproject.domain.dto.request.RecordInterviewRequestDto;
import com.example.umc3_teamproject.domain.dto.request.RecordScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.response.RecordInterviewResponseDto;
import com.example.umc3_teamproject.domain.dto.response.RecordScriptResponseDto;
import com.example.umc3_teamproject.repository.RecordInterviewRepository;
import com.example.umc3_teamproject.repository.RecordRespository;
import com.example.umc3_teamproject.service.RecordInterviewService;
import com.example.umc3_teamproject.service.RecordScriptService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController      // Json 형태로 객체 데이터를 반환 (@Controller + @ResponseBody)
@RequestMapping("/interview")
@RequiredArgsConstructor
@Api(tags = {"RecordInterview Api"})
public class RecordInterviewController {

    private final RecordInterviewService recordinterviewService;



    @PostMapping("/addRecord")
    public ResponseEntity<?> recordInterview(@RequestBody RecordInterviewRequestDto.Register record_interview ){

        return recordinterviewService.recordInterview(record_interview);
    }

}
