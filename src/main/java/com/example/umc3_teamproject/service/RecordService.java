package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
import com.example.umc3_teamproject.domain.dto.request.RecordRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ForumResponseDto;
import com.example.umc3_teamproject.domain.dto.response.MemoResponseDto;
import com.example.umc3_teamproject.domain.dto.response.RecordMemoResponseDto;
import com.example.umc3_teamproject.domain.dto.response.RecordResponseDto;
import com.example.umc3_teamproject.domain.item.*;
import com.example.umc3_teamproject.domain.item.Record;
import com.example.umc3_teamproject.exception.CustomException;
import com.example.umc3_teamproject.exception.ErrorCode;
import com.example.umc3_teamproject.repository.InterviewRepository;
import com.example.umc3_teamproject.repository.MemoRepository;
import com.example.umc3_teamproject.repository.RecordRepository;
import com.example.umc3_teamproject.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {

    private final ScriptRepository scriptRepository;
    private final InterviewRepository interviewRepository;
    private final RecordRepository recordRepository;
    private final MemoRepository memoRepository;


    @Transactional
    public ResponseTemplate<RecordResponseDto.Body> createRecord(String script_or_interview, long id, RecordRequestDto.createRecordRequest request) {
        Record record = new Record();
        int result_count;
        Script findScript = null;
        Interview findInterview = null;
        int total_elapsed_minute;
        int total_elapsed_second;

        if(script_or_interview.equals("script")){
            findScript = getScript(id);
            result_count = findScript.getResult_count();
            total_elapsed_minute = findScript.getTotal_elapsed_minute();
            total_elapsed_second = findScript.getTotal_elapsed_second();
        }else{
            System.out.println("interview 실행");
            findInterview = getInterview(id);
            result_count = findInterview.getResult_count();
            total_elapsed_minute = findInterview.getTotal_elapsed_minute();
            total_elapsed_second = findInterview.getTotal_elapsed_second();
        }
        double score1 = request.getScore1();
        double score2 = request.getScore2();
        double score3 = request.getScore3();
        double score4 = request.getScore4();
        double score5 = request.getScore5();

        double mean = (score1+score2+score3+score4+score5)/5;

        result_count++;

        record.createRecord(
                script_or_interview,
                id,
                result_count,
                score1,score2,score3,score4,score5,mean,request.getElapsed_minute(), request.getElapsed_second()
        );
        recordRepository.save(record);
        total_elapsed_minute += record.getElapsed_minute();
        total_elapsed_second += record.getElapsed_second();
        if(total_elapsed_second >= 60){
            total_elapsed_minute += 1;
            total_elapsed_second -= 60;
        }

        if(script_or_interview.equals("script")){
            updateScriptResultCount(findScript,result_count,total_elapsed_minute,total_elapsed_second);
        }else if (script_or_interview == "interview"){
            updateInterviewResultCount(findInterview,result_count,total_elapsed_minute,total_elapsed_second);
        }
        return getRecordResponse(record);
    }


    @Transactional
    public void updateScriptResultCount(Script script,int result_count
            ,int total_elapsed_minute,int total_elapsed_second){
        script.setResult_count(result_count);
        script.setTotal_elapsed_minute(total_elapsed_minute);
        script.setTotal_elapsed_second(total_elapsed_second);
    }

    @Transactional
    public void updateInterviewResultCount(Interview interview,int result_count
            ,int total_elapsed_minute,int total_elapsed_second){
        interview.setResult_count(result_count);
        interview.setTotal_elapsed_minute(total_elapsed_minute);
        interview.setTotal_elapsed_second(total_elapsed_second);
    }

    //GetMapping("/")
    public ResponseTemplate<RecordMemoResponseDto.Body> getRecord(String script_or_interview, long id) {
        if(script_or_interview.equals("script")){
            return getScriptRecord(id);
        }else if(script_or_interview.equals("interview")){
            return getInterviewRecord(id);
        }

        return null;
    }

    public ResponseTemplate<RecordMemoResponseDto.Body> getScriptRecord(long id){
        Script script = getScript(id);
        int result_count = script.getResult_count();
        int total_elapsed_minute = script.getTotal_elapsed_minute();
        int total_elapsed_second = script.getTotal_elapsed_second();
        List<Memo> memos = memoRepository.findByScriptId(id);
        if(script.getResult_count() >= 2){
            List<Record> records = recordRepository.findByScriptIdLimit2(id);
            double mean = records.get(0).getMean();
            return getRecordMemoResponseList(
                    result_count,mean,total_elapsed_minute,total_elapsed_second,records,memos);
        }else{
            List<Record> records = recordRepository.findByScriptIdLimit1(id);
            double mean = records.get(0).getMean();
            return getRecordMemoResponseList(
                    result_count,mean,total_elapsed_minute,total_elapsed_second,records,memos);
        }
    }

    public ResponseTemplate<RecordMemoResponseDto.Body> getInterviewRecord(long id){
        Interview interview = getInterview(id);
        int result_count = interview.getResult_count();
        int total_elapsed_minute = interview.getTotal_elapsed_minute();
        int total_elapsed_second = interview.getTotal_elapsed_second();
        List<Memo> memos = memoRepository.findByInterviewId(id);
        if(interview.getResult_count() >= 2){
            List<Record> records = recordRepository.findByIntervieIdLimit2(id);
            double mean = records.get(0).getMean();
            return getRecordMemoResponseList(
                    result_count,mean,total_elapsed_minute,total_elapsed_second,records,memos);
        }else{
            System.out.println("여기가 실행되야 됨");
            List<Record> records = recordRepository.findByIntervieIdLimit1(id);
            double mean = records.get(0).getMean();
            return getRecordMemoResponseList(
                    result_count,mean,total_elapsed_minute,total_elapsed_second,records,memos);
        }
    }

    public Script getScript(long id){
        return scriptRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.SCRIPT_NOT_FOUND));
    }

    public Interview getInterview(long id){
        return interviewRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INTERVIEW_NOT_FOUND));
    }

    private ResponseTemplate<List<RecordResponseDto.Body>> getRecordResponseList(List<Record> records,List<Memo> memos) {
        List<RecordResponseDto.Body> recordResponseList = records.stream().map(
                s -> new RecordResponseDto.Body(s.getResult_count(),s.getMean(),
                        s.getScore1(),s.getScore2(),s.getScore3(),s.getScore4(),s.getScore5())).collect(Collectors.toList());

        return new ResponseTemplate<>(recordResponseList);
    }

    private ResponseTemplate<RecordMemoResponseDto.Body> getRecordMemoResponseList(
            int result_count,double mean,int total_elapsed_minute,int toal_elased_second,
            List<Record> records,List<Memo> memos) {
        RecordMemoResponseDto.Body recordMemoResponse = new RecordMemoResponseDto.Body(
                result_count,
                mean,
                total_elapsed_minute,
                toal_elased_second,
                records.stream().map(
                        s -> new RecordResponseDto.Body(s.getResult_count(),s.getMean(),
                                s.getScore1(),s.getScore2(),s.getScore3(),s.getScore4(),s.getScore5())
                ).collect(Collectors.toList()),
                memos.stream().map(memo ->
                                new MemoResponseDto.createBody(memo.getResult_count(),memo.getMemo()))
                        .collect(Collectors.toList()));

        return new ResponseTemplate<>(recordMemoResponse);
    }

    private ResponseTemplate<RecordResponseDto.Body> getRecordResponse(Record record) {
        RecordResponseDto.Body recordResponse = new RecordResponseDto.Body(record.getResult_count(),record.getMean(),
                record.getScore1(),record.getScore2(),record.getScore3(),record.getScore4(),record.getScore5());

        return new ResponseTemplate<>(recordResponse);
    }

    @Transactional
    public void deleteRecord(String type,Long script_interview_id){
        if(type.equals("script")){
            recordRepository.deleteByScriptIdInQuery(script_interview_id);
        }else if(type.equals("interview")){
            recordRepository.deleteByInterviewIdInQuery(script_interview_id);
        }
    }
}
