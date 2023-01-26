package com.example.umc3_teamproject.domain.dto.response;

import com.example.umc3_teamproject.domain.item.InterviewParagraph;
import com.example.umc3_teamproject.domain.item.InterviewParagraphResult;
import com.example.umc3_teamproject.domain.item.VoiceSpeed;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class InterviewResultResponseDto {
    @Data
    @NoArgsConstructor
    public static class Body {

        private String paragraph_title;

        private String paragraph_content;

        @ApiParam(value = "해당 interview 질문에 대한 녹음 파일", required = false, example = "1")
        private String voice_url;

        @ApiParam(value = "경과 시간(분)", required = false, example = "1")
        private int elapsedTimeMinute;

        @ApiParam(value = "경과 시간(초)", required = false, example = "56")
        private int elapsedtimeSecond;

        private String voice_content;

        private Map<String,Integer> caution_vocabularies;

        private VoiceSpeed voiceSpeed;


        public Body(InterviewParagraph interviewParagraph , InterviewParagraphResult interviewParagraphResult) {
            this.paragraph_title = interviewParagraph.getTitle();
            this.paragraph_content = interviewParagraph.getContent();
            this.voice_url = interviewParagraphResult.getVoiceUrl();
            this.elapsedTimeMinute = interviewParagraphResult.getElapsedTimeMinute();
            this.elapsedtimeSecond = interviewParagraphResult.getElapsedTimeSecond();
            this.voice_content = interviewParagraphResult.getVoice_content();
            this.caution_vocabularies = interviewParagraphResult.getCaution_vocabularies();
            this.voiceSpeed = interviewParagraphResult.getVoiceSpeed();
        }
    }
}
