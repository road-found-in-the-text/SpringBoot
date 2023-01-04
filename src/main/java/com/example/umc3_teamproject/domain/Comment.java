package com.example.umc3_teamproject.domain;

import com.example.umc3_teamproject.Forum.domain.Forum;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@SequenceGenerator(
        name = "SEQ_GEN",
        sequenceName = "SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_GEN")
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    private Long writer_id;
    private String comments;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean deleted;

    @OneToMany(mappedBy = "comment")
    private List<NestedComment> nestedComments = new ArrayList<>();

    public void addNestedComment(NestedComment nestedComment){
        nestedComments.add(nestedComment);
        nestedComment.setComment(this);
    }

    //== 비즈니스 로직==//
    public void changeDeleted(boolean deleted){
        this.deleted = deleted;
    }
}
