package com.example.umc3_teamproject.domain.item;

import com.example.umc3_teamproject.domain.Member;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Builder
@Entity
@Table(name="record_interview")
@Where(clause = "deleted = false")
@JsonIdentityReference(alwaysAsId = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class RecordInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member memberId;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "interviewId")
    @JsonManagedReference
    private Interview interviewId;



    @Column
    private Float record;

}
