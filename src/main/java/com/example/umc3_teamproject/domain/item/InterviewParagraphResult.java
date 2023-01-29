package com.example.umc3_teamproject.domain.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Where(clause = "deleted_status = false")
@Table(name = "interview_paragraph_result")
@AllArgsConstructor
@NoArgsConstructor
public class InterviewParagraphResult {
    @Id                   // Primary Key와 연결
    @GeneratedValue(strategy = GenerationType.IDENTITY)       // 식별자 값을 자동 생성
    @Column(name="interview_paragraph_result_id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @OneToOne
    @JoinColumn(name = "interview_paragraph_id",referencedColumnName = "interview_paragraph_id",unique = true)
    private InterviewParagraph interviewParagraph;

    private String paragraph_title;

    private String paragraph_content;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime endTime;

    private int elapsedTimeMinute;
    private int elapsedTimeSecond;

    @Column(length = 4000)
    private String voiceUrl;

    @Column(length = 4000)
    private String storeFilename;

    @Column(length = 6000)
    private String voice_content;

    @Enumerated(EnumType.STRING)
    private VoiceSpeed voiceSpeed;

    @ElementCollection
    private Map<String,Integer> caution_vocabularies = new HashMap<>();

    private Boolean deleted_status;

    public void createInterviewParagraphResult(InterviewParagraph interviewParagraph,LocalDateTime startTime, LocalDateTime endTime, String voiceUrl, String storeFilename, String voice_content, VoiceSpeed voiceSpeed, Map<String, Integer> caution_vocabularies, Boolean deleted_status,
                                               int elapsedTimeMinute,int elapsedTimeSecond) {
        this.interviewParagraph = interviewParagraph;
        this.paragraph_title = interviewParagraph.getTitle();
        this.paragraph_content = interviewParagraph.getContent();
        this.startTime = startTime;
        this.endTime = endTime;
        this.elapsedTimeMinute = elapsedTimeMinute;
        this.elapsedTimeSecond = elapsedTimeSecond;
        this.voiceUrl = voiceUrl;
        this.storeFilename = storeFilename;
        this.voice_content = voice_content;
        this.voiceSpeed = voiceSpeed;
        this.caution_vocabularies = caution_vocabularies;
        this.deleted_status = deleted_status;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }
}
