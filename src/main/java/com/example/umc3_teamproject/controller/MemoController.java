package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.request.MemoRequestDto;
import com.example.umc3_teamproject.domain.dto.response.MemoResponseDto;
import com.example.umc3_teamproject.service.MemoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Result Memo Api"})
@RequestMapping("/record/{script-or-interview}/{script_id-or-interview_id}/memo")
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/new")
    public ResponseTemplate<MemoResponseDto.createBody> createMemo(
            @PathVariable("script-or-interview") String script_or_interview,
            @PathVariable("script_id-or-interview_id") long id,
            @RequestBody @Validated MemoRequestDto.createRequest request){
        return memoService.createMemo(script_or_interview,id,request);
    }

    @PutMapping("/edit/{memo-id}")
    public ResponseTemplate<MemoResponseDto.updateBody> updateMemo(@PathVariable("memo-id") Long memo_id,
                                                                   @RequestBody @Validated MemoRequestDto.updateRequest request){
        return memoService.updateMemo(memo_id,request);
    }

    @DeleteMapping("/delete/{memo-id}")
    public String deleteMemo(@PathVariable("memo-id") long memo_id){
        return memoService.deleteByMemoId(memo_id);
    }

//    @GetMapping("/script/{script-id}")
//    public ResponseTemplate<MemoResponseDto.updateBody> getMemo(@PathVariable("memo-id") Long memo_id){
//        return memoService.getMemo(memo_id);
//    }
//
//    @GetMapping("/interview/{script-id}")
//    public ResponseTemplate<MemoResponseDto.updateBody> getMemo(@PathVariable("memo-id") Long memo_id){
//        return memoService.getMemo(memo_id);
//    }

}
