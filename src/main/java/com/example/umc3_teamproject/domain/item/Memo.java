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

    private String type;

    // 해당 script와 interview의 id
    private long script_interview_id;

    // 회차 저장
    private int result_count;

    // 메모 저장
    private String memo;

    // 비즈니스 로직
    public void createMemo(String type,long script_interview_id, int result_count,String memo){
        this.type = type;
        this.script_interview_id = script_interview_id;
        this.result_count = result_count;
        this.memo = memo;
    }

    public void changeMemo(String memo){
        this.memo = memo;
    }
}
