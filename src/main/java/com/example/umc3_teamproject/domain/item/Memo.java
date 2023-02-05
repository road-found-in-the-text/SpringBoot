package com.example.umc3_teamproject.domain.item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "memo")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_memo_id")
    private Long id;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="script_id")
    private Script script;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    private String memo;

    // 비즈니스 로직
    public void createMemo(Script script, Interview interview, String memo){
        if(script != null){
            this.script = script;
        }

        if(interview != null){
            this.interview = interview;
        }

        this.memo = memo;
    }

    public void changeMemo(String memo){
        this.memo = memo;
    }
}
