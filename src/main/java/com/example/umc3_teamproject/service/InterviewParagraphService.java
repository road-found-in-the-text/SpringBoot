package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.request.InterviewParagraphRequestDto;
import com.example.umc3_teamproject.domain.dto.response.InterviewParagraphResponseDto;
import com.example.umc3_teamproject.domain.item.Interview;
import com.example.umc3_teamproject.domain.item.InterviewParagraph;
import com.example.umc3_teamproject.domain.item.InterviewParagraphResult;
import com.example.umc3_teamproject.exception.CustomException;
import com.example.umc3_teamproject.exception.ErrorCode;
import com.example.umc3_teamproject.repository.InterviewParagraphRepository;
import com.example.umc3_teamproject.repository.InterviewParagraphResultRepository;
import com.example.umc3_teamproject.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewParagraphService {

    private final InterviewParagraphRepository interviewParagraphRepository;
    private final InterviewRepository interviewRepository;

    private final InterviewParagraphResultRepository interviewParagraphResultRepository;

    @Transactional
    public ResponseTemplate<InterviewParagraphResponseDto.Body> writeParagraph(InterviewParagraphRequestDto.Register request) {
        Interview findInterview = interviewRepository.findById(request.getInterview_id()).orElseThrow(
                () -> new CustomException(ErrorCode.INTERVIEW_NOT_FOUND)
        );

        InterviewParagraph interviewParagraph = InterviewParagraph.builder()
                .interview(findInterview)
                .paragraph_order(request.getParagraph_order())
                .content(request.getContent())
                .title(request.getTitle())
                .deleted(false)
                .build();
        interviewParagraph.setCreatedDate(LocalDateTime.now());
        interviewParagraph.setModifiedDate(LocalDateTime.now());

        interviewParagraphRepository.save(interviewParagraph);
        return new ResponseTemplate<>(new InterviewParagraphResponseDto.Body(
                interviewParagraph.getInterview().getInterviewId(),interviewParagraph.getId(),
                interviewParagraph.getTitle(),interviewParagraph.getContent(),interviewParagraph.getParagraph_order(),
                interviewParagraph.getCreatedDate(),interviewParagraph.getModifiedDate()
        ));
    }

    @Transactional
    public ResponseTemplate<InterviewParagraphResponseDto.Body> updateParagraph(Long paragraph_id, InterviewParagraphRequestDto.Update request) {
        InterviewParagraph findInterviewParagraph = interviewParagraphRepository.findById(paragraph_id).orElseThrow(
                () -> new CustomException(ErrorCode.INTERVIEW_PARAGRAPH_NOT_FOUND)
        );

        findInterviewParagraph.updateInterviewParagraph(request.getTitle(),request.getContent(),request.getParagraph_order());
        return new ResponseTemplate<>(new InterviewParagraphResponseDto.Body(
                findInterviewParagraph.getInterview().getInterviewId(),findInterviewParagraph.getId(),
                findInterviewParagraph.getTitle(),findInterviewParagraph.getContent(),findInterviewParagraph.getParagraph_order(),
                findInterviewParagraph.getCreatedDate(),findInterviewParagraph.getModifiedDate()
        ));
    }

    public ResponseTemplate<List<InterviewParagraphResponseDto.Body>> getParagraphByInterviewId(Long interview_id) {
        Interview findInterview = interviewRepository.findById(interview_id).orElseThrow(
                () -> new CustomException(ErrorCode.INTERVIEW_NOT_FOUND)
        );
        List<InterviewParagraphResponseDto.Body> collect = interviewParagraphRepository.findByIntervieId(interview_id).stream().map(
                interviewParagraph -> new InterviewParagraphResponseDto.Body(
                        interviewParagraph.getInterview().getInterviewId(), interviewParagraph.getId(),
                        interviewParagraph.getTitle(), interviewParagraph.getContent(), interviewParagraph.getParagraph_order(),
                        interviewParagraph.getCreatedDate(), interviewParagraph.getModifiedDate()
                )
        ).collect(Collectors.toList());
        return new ResponseTemplate<>(collect);
    }

    public ResponseTemplate<InterviewParagraphResponseDto.Body> getParagraphByParagraphId(Long paragraph_id) {
        InterviewParagraph findInterviewParagraph = interviewParagraphRepository.findById(paragraph_id).orElseThrow(
                () -> new CustomException(ErrorCode.INTERVIEW_PARAGRAPH_NOT_FOUND)
        );
        return new ResponseTemplate<>(new InterviewParagraphResponseDto.Body(
                findInterviewParagraph.getInterview().getInterviewId(),findInterviewParagraph.getId(),
                findInterviewParagraph.getTitle(),findInterviewParagraph.getContent(),findInterviewParagraph.getParagraph_order(),
                findInterviewParagraph.getCreatedDate(),findInterviewParagraph.getModifiedDate()
        ));
    }

    @Transactional
    public ResponseTemplate<String> remove(Long paragraph_id){
        InterviewParagraph interviewParagraph = interviewParagraphRepository.findById(paragraph_id).orElseThrow(
                () -> new CustomException(ErrorCode.INTERVIEW_PARAGRAPH_NOT_FOUND)
        );
        interviewParagraph.deleted_status_true();
        InterviewParagraphResult interviewParagraphResultByInterviewParagraphId = interviewParagraphResultRepository.findInterviewParagraphResultByInterviewParagraphId(paragraph_id);
        interviewParagraphResultRepository.delete(interviewParagraphResultByInterviewParagraphId);
        return new ResponseTemplate<>(paragraph_id+" deleted success");
    }



}
