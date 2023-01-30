package com.example.umc3_teamproject.domain.item;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Table(name = "interview_paragraph")
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
public class InterviewParagraph extends BaseEntity{

    @Id                   // Primary Key와 연결
    @GeneratedValue(strategy = GenerationType.IDENTITY)       // 식별자 값을 자동 생성
    @Column(name="interview_paragraph_id")
    private Long Id;

    private String title;               // 제목
    private String content;             // 본문
    private int paragraph_order;        // paragraph들 간의 순서 (나중에 결과 도출할 때 사용합니다

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @OneToOne(mappedBy = "interviewParagraph")
    private InterviewParagraphResult interviewParagraphResult;

    private boolean deleted;


    // 로직 메서드
    public void deleted_status_true(){
        this.deleted = true;
    }

    public void updateInterviewParagraph(String title, String content, int paragraph_order){
        this.title = title;
        this.content = content;
        this.paragraph_order = paragraph_order;
        this.setModifiedDate(LocalDateTime.now());
    }

}
