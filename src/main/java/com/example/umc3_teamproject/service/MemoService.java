package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.request.MemoRequestDto;
import com.example.umc3_teamproject.domain.dto.response.MemoResponseDto;
import com.example.umc3_teamproject.domain.item.Interview;
import com.example.umc3_teamproject.domain.item.InterviewParagraph;
import com.example.umc3_teamproject.domain.item.Memo;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.exception.CustomException;
import com.example.umc3_teamproject.exception.ErrorCode;
import com.example.umc3_teamproject.repository.InterviewRepository;
import com.example.umc3_teamproject.repository.MemoRepository;
import com.example.umc3_teamproject.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Template;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;
    private final ScriptRepository scriptRepository;
    private final InterviewRepository interviewRepository;

    @Transactional
    public ResponseTemplate<MemoResponseDto.createBody> createMemo(Long script_interview_id,MemoRequestDto.createRequest request){
        Memo memo = new Memo();
        if(request.getType().equals("script")){
            Script findScript = scriptRepository.findById(script_interview_id).orElseThrow(
                    () -> new CustomException(ErrorCode.SCRIPT_NOT_FOUND)
            );
            memo.createMemo(findScript,null, request.getMemo());
        }else if(request.getType().equals("interview")){
            Interview findInterview = interviewRepository.findById(script_interview_id).orElseThrow(
                    () -> new CustomException(ErrorCode.INTERVIEW_NOT_FOUND)
            );
            memo.createMemo(null,findInterview, request.getMemo());
        }
        memoRepository.save(memo);
        return new ResponseTemplate<>(new MemoResponseDto.createBody(
                request.getType(),script_interview_id,memo.getId(),memo.getMemo()));
    }

    @Transactional
    public ResponseTemplate<MemoResponseDto.updateBody> updateMemo(Long memo_id,MemoRequestDto.updateRequest request){
        Memo findMemo = memoRepository.findById(memo_id).orElseThrow(
                () -> new CustomException(ErrorCode.MEMO_NOT_FOUND)
        );

        findMemo.changeMemo(request.getMemo());

        return new ResponseTemplate<>(new MemoResponseDto.updateBody(
                findMemo.getId(),findMemo.getMemo()));
    }


    @Transactional
    public void deleteMemo(String type,Long script_interview_id){
        if(type.equals("script")){
            memoRepository.deleteByScriptIdInQuery(script_interview_id);
        }else if(type.equals("interview")){
            memoRepository.deleteByInterviewIdInQuery(script_interview_id);
        }
    }

    public ResponseTemplate<MemoResponseDto.updateBody> getMemo(Long memo_id) {
        Memo findMemo = memoRepository.findById(memo_id).orElseThrow(
                () -> new CustomException(ErrorCode.MEMO_NOT_FOUND)
        );

        return new ResponseTemplate<>(new MemoResponseDto.updateBody(
                findMemo.getId(),findMemo.getMemo()));
    }
}
