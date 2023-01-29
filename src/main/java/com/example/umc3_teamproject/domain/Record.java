package com.example.umc3_teamproject.domain;

import com.example.umc3_teamproject.domain.item.Forum;
import com.example.umc3_teamproject.domain.item.Interview;
import com.example.umc3_teamproject.domain.item.Script;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity(name="Record")
@Table(name="record")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Float record;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "script_id")
    private Script script;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;


}


