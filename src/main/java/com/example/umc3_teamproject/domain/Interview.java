package com.example.umc3_teamproject.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(
        name = "SEQ_GEN",
        sequenceName = "SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERVIEW_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String type;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean deleted;

    public Interview(User user, String title, String type, InterviewParagraph... interviewParagraphs) {
        this.user = user;
        this.title = title;
        this.type = type;
        for(InterviewParagraph interviewParagraph: interviewParagraphs){
            this.addInterviewParagraph(interviewParagraph);
        }
        this.deleted = false;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "interview",cascade = CascadeType.ALL)
    private List<InterviewParagraph> interviewParagraphs = new ArrayList<>();

    //== 연관 메서드 ==//
    public void addInterviewParagraph(InterviewParagraph interviewParagraph){
        interviewParagraphs.add(interviewParagraph);
        interviewParagraph.setInterview(this);
    }

    //== 생성 메서드 ==//
    public Interview createInterview(User user,String title,String type,InterviewParagraph... interviewParagraphs){
        Interview interview = new Interview(user,title,type,interviewParagraphs);
        return interview;
    }

    //== 비즈니스 로직==//
    public void changeDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public void setUser(User user){
        this.user = user;
    }
}
