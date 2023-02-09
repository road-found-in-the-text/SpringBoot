package com.example.umc3_teamproject.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long id;

    // 점수를 담는 곳
    private double score1;
    private double score2;
    private double score3;
    private double score4;
    private double score5;

    private double mean;

    // 회차 담는 곳
    private int result_count;

    // sciprt와 intervie 구분
    private String type;

    // 해당 script와 interview의 id
    private long script_interview_id;

    private int elapsed_minute;
    private int elapsed_second;

    // 비즈니스 로직
    public void createRecord(String type, long script_interview_id,int result_count,
                             double score1,double score2,double score3,double score4,double score5,double mean
    ,int elapsed_minute,int elapsed_second){
        this.type = type;
        this.script_interview_id = script_interview_id;
        this.result_count = result_count;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
        this.score5 = score5;
        this.mean = mean;
        this.elapsed_minute = elapsed_minute;
        this.elapsed_second = elapsed_second;
        this.setCreatedDate(LocalDateTime.now());
        this.setModifiedDate(LocalDateTime.now());
    }






}
