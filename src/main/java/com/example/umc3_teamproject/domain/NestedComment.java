package com.example.umc3_teamproject.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@SequenceGenerator(
        name = "SEQ_GEN",
        sequenceName = "SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
public class NestedComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_GEN")
    @Column(name = "NESTED_COMMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private Long writer_id;
    private String comments;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean deleted;

    //== 비즈니스 로직==//
    public void changeDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public void setComment(Comment comment){
        this.comment = comment;
    }
}
