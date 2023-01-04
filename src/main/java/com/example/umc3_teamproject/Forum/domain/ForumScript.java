package com.example.umc3_teamproject.Forum.domain;

import com.example.umc3_teamproject.Forum.domain.Forum;
import com.example.umc3_teamproject.domain.Script;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@SequenceGenerator(
        name = "ForumScript_SEQ_GEN",
        sequenceName = "ForumScript_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ForumScript {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ForumScript_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @ManyToOne
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
