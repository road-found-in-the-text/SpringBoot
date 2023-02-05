package com.example.umc3_teamproject.domain.item;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.item.Forum;
import com.example.umc3_teamproject.domain.item.Interview;
import com.example.umc3_teamproject.domain.item.Script;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
@Builder
@Entity
@Table(name="record_script")
@Where(clause = "deleted = false")
@JsonIdentityReference(alwaysAsId = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RecordScript {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // 식별자 값을 자동 생성
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member memberId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "script_id")
    @JsonManagedReference
    private Script scriptId;

    @Column
    private Float record;



}