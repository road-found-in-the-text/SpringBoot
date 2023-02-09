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
    public ResponseTemplate<MemoResponseDto.createBody> createMemo(
            String script_or_interview,long id,MemoRequestDto.createRequest request){
        int result_count;
        Script findScript = null;
        Interview findInterview = null;

        Memo memo = new Memo();
        if(script_or_interview.equals("script")){
            findScript = getScript(id);
            result_count = findScript.getResult_count();
        }else{
            findInterview = getInterview(id);
            result_count = findInterview.getResult_count();
        }
        memo.createMemo(script_or_interview,id,result_count,request.getMemo());
        memoRepository.save(memo);

        return new ResponseTemplate<>(new MemoResponseDto.createBody(
                memo.getResult_count(),memo.getMemo()));
    }

    public Script getScript(long id){
        return scriptRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.SCRIPT_NOT_FOUND));
    }

    public Interview getInterview(long id){
        return interviewRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INTERVIEW_NOT_FOUND));
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

    public String deleteByMemoId(long memo_id) {
        memoRepository.deleteById(memo_id);
        return memo_id + "인 메모 삭제 완료";
    }
}
