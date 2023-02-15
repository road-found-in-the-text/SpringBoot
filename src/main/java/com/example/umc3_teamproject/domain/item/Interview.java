package com.example.umc3_teamproject.domain.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Builder
@Entity
@Table(name = "interview")
@Getter
@Setter
// @SQLDelete(sql = "UPDATE umc3.script SET deleted = true WHERE script_id = ?")   // JPA Soft Delete
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
public class Interview extends BaseEntity {

    @Id                   // Primary Key와 연결
    @GeneratedValue(strategy = GenerationType.AUTO)       // 식별자 값을 자동 생성
    @Column(name="interviewId", updatable = false)
    private Long interviewId;

    @Column(name="userId", updatable = false)
    private Long userId;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "user_id")
//    private Member member;

    @Column
    private String title;

    @Column
    private String type;

    @Column
    private int result_count;

    @Column
    private int total_elapsed_minute;

    @Column
    private int total_elapsed_second;

    @Column
    private boolean deleted;

    @Column
    private LocalDate interviewDate;


    // interiview paragraph는 InterviewParagraph로 새로 Entity 만들었어요!
//     //paragraph를 list로 추가
//     @OneToMany(mappedBy = "scriptId")
//     @JsonBackReference
//     private List<Paragraph> paragraphList = new ArrayList<>();

    @OneToMany(mappedBy = "interview",orphanRemoval = true)
    private List<InterviewParagraph> interviewParagraphs = new ArrayList<>();

    @OneToMany(mappedBy = "interview",orphanRemoval = true)
    private List<InterviewParagraphResult> interviewParagraphResults = new ArrayList<>();


    @OneToMany(mappedBy = "interviewId", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<RecordInterview> record_interview;
}
