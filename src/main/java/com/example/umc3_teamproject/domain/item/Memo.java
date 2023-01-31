package com.example.umc3_teamproject.domain.item;

import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity @Table(name="memo") @AllArgsConstructor
public class Memo {

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="script_id")
    private Script script;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

}
