package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
import com.example.umc3_teamproject.domain.dto.request.RecordRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ForumResponseDto;
import com.example.umc3_teamproject.domain.dto.response.RecordMemoResponseDto;
import com.example.umc3_teamproject.domain.dto.response.RecordResponseDto;
import com.example.umc3_teamproject.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.netty.http.server.HttpServerRequest;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Forum Api"})
@RequestMapping("/record/{script-or-interview}/{script_id-or-interview_id}")
public class RecordController {

    private final RecordService recordService;

    @ApiOperation(value = "record 저장", notes = "결과를 저장한다.")
    @PostMapping("/new")
    public ResponseTemplate<RecordResponseDto.Body> createRecord(
            @PathVariable("script-or-interview") String script_or_interview,
            @PathVariable("script_id-or-interview_id") long id,
            @Validated @RequestBody RecordRequestDto.createRecordRequest request) throws IOException, ResponseException {
        return recordService.createRecord(script_or_interview,id,request);
    }

    @ApiOperation(value = "결과 페이지 생성", notes = "결과를 보여준다.")
    @GetMapping("")
    public ResponseTemplate<RecordMemoResponseDto.Body> getRecord(
            @PathVariable("script-or-interview") String script_or_interview,
            @PathVariable("script_id-or-interview_id") long id) throws IOException, ResponseException {
        return recordService.getRecord(script_or_interview, id);
    }

}
