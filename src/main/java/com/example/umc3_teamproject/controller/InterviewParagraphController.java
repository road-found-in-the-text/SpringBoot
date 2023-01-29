package com.example.umc3_teamproject.controller;
import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.request.CommentRequestDto;
import com.example.umc3_teamproject.domain.dto.request.InterviewParagraphRequestDto;
import com.example.umc3_teamproject.domain.dto.request.ParagraphRequestDto;
import com.example.umc3_teamproject.domain.dto.response.InterviewParagraphResponseDto;
import com.example.umc3_teamproject.domain.dto.response.ParagraphResponseDto;
import com.example.umc3_teamproject.domain.item.Paragraph;
import com.example.umc3_teamproject.repository.InterviewParagraphRepository;
import com.example.umc3_teamproject.repository.ParagraphRepository;
import com.example.umc3_teamproject.service.InterviewParagraphService;
import com.example.umc3_teamproject.service.ParagraphService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/interview/paragraph")
@RequiredArgsConstructor
public class InterviewParagraphController {

    private final InterviewParagraphService interviewParagraphService;

    @PostMapping("/new")
    public ResponseTemplate<InterviewParagraphResponseDto.Body> writeParagraph(@RequestBody @Validated InterviewParagraphRequestDto.Register write){
        return interviewParagraphService.writeParagraph(write);
    }

    @PutMapping("/edit/{interviewParagraph-id}")
    public ResponseTemplate<InterviewParagraphResponseDto.Body> update(@PathVariable("interviewParagraph-id") Long paragraph_id,
                                                                       @RequestBody @Validated InterviewParagraphRequestDto.Update request) {
        return interviewParagraphService.updateParagraph(paragraph_id,request);
    }
    @DeleteMapping("/delete/{interviewParagraph-id}")
    public ResponseTemplate<String> deleteParagraph(@PathVariable("interviewParagraph-id") Long paragraph_id) {
        return interviewParagraphService.remove(paragraph_id);

    }

    @GetMapping("/interview/{interviewId}")
    public ResponseTemplate<List<InterviewParagraphResponseDto.Body>> readParagraphByInterviewId(@PathVariable("interviewId") Long interviewId) {
        return interviewParagraphService.getParagraphByInterviewId(interviewId);
    }

    @GetMapping("/{interviewParagraph-id}")
    public ResponseTemplate<InterviewParagraphResponseDto.Body> readParagraphByParagraphId(@PathVariable("interviewParagraph-id") Long paragraph_id) {
        return interviewParagraphService.getParagraphByParagraphId(paragraph_id);
    }

}