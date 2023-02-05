package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.dto.request.RecordInterviewRequestDto;
import com.example.umc3_teamproject.domain.dto.response.RecordInterviewResponseDto;
import com.example.umc3_teamproject.domain.item.Interview;
import com.example.umc3_teamproject.domain.item.RecordInterview;
import com.example.umc3_teamproject.domain.item.RecordScript;
import com.example.umc3_teamproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RecordInterviewService {
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final InterviewRepository interviewRepository;
    @Autowired
    private final RecordInterviewRepository recordinterviewRepository;

    private final RecordInterviewResponseDto recordinterviewResponse;


    public ResponseEntity<?> recordInterview(RecordInterviewRequestDto.Register record_interview1) {

        Member interview_member = memberRepository.getUser(record_interview1.getMemberId());
        Interview record1_interview = interviewRepository.getById(record_interview1.getInterviewId());


        RecordInterview record_interview= RecordInterview.builder()
                .memberId(interview_member)
                .interviewId(record1_interview)
                .record(record_interview1.getRecord())
                .build();
        recordinterviewRepository.save(record_interview);
        return recordinterviewResponse.success(record_interview);
    }
    @Transactional
    public void saveItem(RecordInterview recordInterview) {

        recordinterviewRepository.save(recordInterview);


    }

}
