package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.dto.request.InterviewRequestDto;
import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.response.InterviewResponseDto;
import com.example.umc3_teamproject.domain.item.Interview;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.InterviewRepository;
import com.example.umc3_teamproject.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterviewService {

    @Autowired
    private final InterviewRepository interviewRepository;
    @Autowired
    private final InterviewResponseDto interviewResponse;

    public ResponseEntity<?> writeInterview(InterviewRequestDto.Register interview1) {

        Interview interview=Interview.builder()
                .userId(interview1.getUserId())
                .title(interview1.getTitle())
                .type(interview1.getType())
                .interviewDate(interview1.getInterviewDate())
                .deleted(false)
                .build();
        interviewRepository.save(interview);

        return interviewResponse.success(interview);
    }

    public Interview updateInterview(Long id, InterviewRequestDto.Update interview1) {

        Optional<Interview> optionalProduct=interviewRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Interview before_interview = optionalProduct.get();

            // 해당 id를 적으면 update 작동

            Interview updatedInterview=new Interview();
            updatedInterview.setInterviewId(id);
            updatedInterview.setUserId(before_interview.getUserId());
            updatedInterview.setCreatedDate(before_interview.getCreatedDate());
            updatedInterview.setInterviewDate(interview1.getInterviewDate());
            updatedInterview.setTitle(interview1.getTitle());
            updatedInterview.setType(interview1.getType());
            interviewRepository.save(updatedInterview);

            return updatedInterview;
        }
        return null;


    }

    public String removeInterview(Long id){
        Optional<Interview> optionalProduct=interviewRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Interview before_interview = optionalProduct.get();

            Interview deletedInterview=new Interview();
            deletedInterview.setInterviewId(id);
            deletedInterview.setUserId(before_interview.getUserId());
            deletedInterview.setInterviewDate(before_interview.getInterviewDate());
            deletedInterview.setCreatedDate(before_interview.getCreatedDate());
            deletedInterview.setTitle(before_interview.getTitle());
            deletedInterview.setType(before_interview.getType());
            deletedInterview.setDeleted(true);
            interviewRepository.save(deletedInterview);

            return id+" deleted success";
        }
        return null;
    }
}
