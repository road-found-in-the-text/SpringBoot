package com.example.umc3_teamproject.domain.item;

import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.dto.MemberRes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "deleted_status = false")
public class NestedComment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NESTED_COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private String content;
    private boolean deleted_status;
    private int like_num;

    //== 비즈니스 로직==//
    public void createNestedComment(String content, Comment comment, Member member){
        this.member = member;
        this.content = content;
        this.comment = comment;
        this.setCreatedDate(LocalDateTime.now());
        this.setModifiedDate(LocalDateTime.now());
    }

    public void updateNestedComment(String content){
        this.content = content;
        this.setModifiedDate(LocalDateTime.now());

    }

    //== 비즈니스 로직==//
    public void deleteComment() {
        this.deleted_status = true;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
