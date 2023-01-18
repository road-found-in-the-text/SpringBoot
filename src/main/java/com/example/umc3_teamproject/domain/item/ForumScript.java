package com.example.umc3_teamproject.domain.item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "deleted_status = false")
public class ForumScript extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ForumScript_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "script_id")
    private Script script;

    private boolean deleted_status;

    //== 비즈니스 로직 ==//
    public void setForum(Forum forum){
        this.forum = forum;
    }

    public void setScript(Script script){
        this.script = script;
    }

    public void deleteScript(){
        this.deleted_status = true;
    }

    public void setDeleted_status(boolean deleted_status){
        this.deleted_status = deleted_status;
    }

    @Builder
    public ForumScript(Forum forum,Script script){
        this.forum = forum;
        this.script = script;
        this.deleted_status = false;
    }
    //== 생성 메서드 ==//

}
