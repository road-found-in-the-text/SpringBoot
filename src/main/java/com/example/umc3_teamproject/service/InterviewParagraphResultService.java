package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
import com.example.umc3_teamproject.domain.dto.request.InterviewRequestDto;
import com.example.umc3_teamproject.domain.dto.request.InterviewResultRequestDto;
import com.example.umc3_teamproject.domain.dto.request.InterviewResultRequestLocalDateTimeDto;
import com.example.umc3_teamproject.domain.dto.response.InterviewResultResponseDto;
import com.example.umc3_teamproject.domain.item.*;
import com.example.umc3_teamproject.exception.CustomException;
import com.example.umc3_teamproject.exception.ErrorCode;
import com.example.umc3_teamproject.repository.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.types.dsl.StringPath;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.TrueFalseType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterviewParagraphResultService {

    private final InterviewParagraphResultRepository interviewParagraphResultRepository;

    private final InterviewParagraphRepository interviewParagraphRepository;

    private final InterviewRepository interviewRepository;

    private final S3Uploader s3Uploader;
    @Transactional
    public ResponseTemplate<String> createInterviewResult(Long interviewParagraph_id,
                                                                                   InterviewResultRequestLocalDateTimeDto.createInterviewResult request) throws IOException {

        InterviewParagraph findInterviewParagraph = interviewParagraphRepository.findById(interviewParagraph_id).orElseThrow(() ->
                new CustomException(ErrorCode.INTERVIEW_NOT_FOUND));

        String paragraph_title = findInterviewParagraph.getTitle();
        String paragraph_content = findInterviewParagraph.getContent();
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime();
        String voiceUrl = uploadImageToInterviewParagraphResult(request.getVoiceFile());
        String storeFilename = StringUtils.getFilename((voiceUrl));
        String voice_content = request.getVoiceContent();
        long elapsedTime_by_second = ChronoUnit.SECONDS.between(startTime,endTime);
        int elapsedTime_minute = (int)elapsedTime_by_second/60;
        int elapsedTime_second = (int)elapsedTime_by_second%60;

        StringTokenizer stringTokenizer = new StringTokenizer(voice_content);
        Map<String,Integer> caution_voacabulary = new HashMap<>();
        int tokensCount  = stringTokenizer.countTokens();
        int total_vocie_String_num = 0;
        String find_caution_vocabulary = null;

        // 녹음 파일 글자수 세기 및 주의 단어 포함 여부 확인
        while(stringTokenizer.hasMoreTokens()){
            String s= stringTokenizer.nextToken();
            total_vocie_String_num += s.length();
            if((find_caution_vocabulary = evaluateCautionVocabulary(s)) != null){
                if(caution_voacabulary.containsKey(find_caution_vocabulary)){
                    caution_voacabulary.put(find_caution_vocabulary,caution_voacabulary.get(find_caution_vocabulary)+1);
                }else{
                    caution_voacabulary.put(find_caution_vocabulary,1);
                }
            }
        }
        System.out.println(caution_voacabulary);

        // 속도 평가
        double string_per_minute = (double)total_vocie_String_num/elapsedTime_by_second*60;
        VoiceSpeed voiceSpeed;
        if(string_per_minute > 400){
            voiceSpeed = VoiceSpeed.fast;
        }else if(string_per_minute < 300){
            voiceSpeed = VoiceSpeed.slow;
        }else{
            voiceSpeed = VoiceSpeed.proper;
        }
        System.out.println(voiceSpeed);

        // InterviewParagraphResult 객체 생성 및 db에 저장.
        InterviewParagraphResult interviewParagraphResult = new InterviewParagraphResult();
        interviewParagraphResult.createInterviewParagraphResult(
                findInterviewParagraph,startTime,endTime,voiceUrl,
                storeFilename,voice_content,voiceSpeed,caution_voacabulary,false,elapsedTime_minute,elapsedTime_second
        );
        interviewParagraphResult.setInterview(findInterviewParagraph.getInterview());
        interviewParagraphResultRepository.save(interviewParagraphResult);

        return new ResponseTemplate<>("결과 생성 되었습니다");
    }

    @Transactional
    public String uploadImageToInterviewParagraphResult(MultipartFile voiceFile) throws IOException {
        String voice_url = s3Uploader.upload(voiceFile, "forumImage");
        return voice_url;
    }

    public String evaluateCautionVocabulary(String s){
        String[] connection_ending = {
                "그래서","그런데","그리고","그러하여",""
        };
        String[] caution_vocabulary_1={
            "음","오","아","어"
        };

        String[] caution_vocabulary_2 = {
                "그냥","딱히","글쎄",
                "무조건",
                "맡겨만",
                "같습니다"
        };
        String find_caution_vocabulary = null;

        for(int i = 0 ; i < caution_vocabulary_1.length;i++){
            if(s.startsWith(caution_vocabulary_1[i]) && s.endsWith(caution_vocabulary_1[i])){
                find_caution_vocabulary = caution_vocabulary_1[i];
                System.out.println(s);
            }
        }

        for(int i = 0 ; i < caution_vocabulary_2.length;i++){
            if(s.startsWith(caution_vocabulary_2[i])){
                find_caution_vocabulary = caution_vocabulary_2[i];
                System.out.println(s);
            }
        }

        return find_caution_vocabulary;
    }

    public ResponseTemplate<List<InterviewResultResponseDto.Body>> getInterviewResult(long interview_id){
        Interview findInterview = interviewRepository.findById(interview_id).orElseThrow(
                () -> new CustomException(ErrorCode.INTERVIEW_NOT_FOUND)
        );

        List<InterviewResultResponseDto.Body> bodyStream = interviewParagraphResultRepository.findByIntervieId(interview_id).stream().map(
                interviewParagraphResult ->
                        new InterviewResultResponseDto.Body(interviewParagraphResult.getInterviewParagraph(), interviewParagraphResult)
        ).collect(Collectors.toList());

        return new ResponseTemplate<>(bodyStream);
    }

    @Transactional
    public void deleteInterviewParagraphResult(Long paragraph_id){
        InterviewParagraphResult findInterviewParagraphResult = interviewParagraphResultRepository.findInterviewParagraphResultByInterviewParagraphId(
                paragraph_id
        );
        if(findInterviewParagraphResult != null){
        interviewParagraphResultRepository.delete(findInterviewParagraphResult);
        }
    }

}
