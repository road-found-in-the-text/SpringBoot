package com.example.umc3_teamproject.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;


@Entity
@Getter
@SequenceGenerator(
        name = "SEQ_GEN",
        sequenceName = "SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
public class InterviewParagraph {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_GEN")
    @Column(name = "ITV_PARAGRAPH_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "script_id")
    private Interview interview;

    private String cotents;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean deleted;
    private String record;
    private int score;

    // 이미지는 어떻게 해야 하는지 알아봐야겠다.

    //== 비즈니스 로직==//
    public void changeDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public void setInterview(Interview interview){
        this.interview = interview;
    }

}
