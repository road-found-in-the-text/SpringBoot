package com.example.umc3_teamproject.domain.item;


import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.item.BaseEntity;
import com.example.umc3_teamproject.dto.MemberRes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "deleted_status = false")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @ManyToOne(fetch = LAZY,cascade = ALL)
    @JoinColumn(name = "user_id")
    private Member member;

    private String content;
    private boolean deleted_status;
    private int like_num;

    @OneToMany(mappedBy = "comment",orphanRemoval = true,cascade = ALL)
    private List<NestedComment> nestedComments = new ArrayList<>();

    public void addNestedComment(NestedComment nestedComment){
        nestedComments.add(nestedComment);
        nestedComment.setComment(this);
    }

    public void createComment(String content, Forum forum, Member member){
        this.member = member;
        this.content = content;
        this.forum = forum;
        this.setCreatedDate(LocalDateTime.now());
        this.setModifiedDate(LocalDateTime.now());
    }

    public void updateComment(String content){
        this.content = content;
        this.setModifiedDate(LocalDateTime.now());

    }


    //== 비즈니스 로직==//
    public void deleteComment() {
        this.deleted_status = true;
    }


}
