package com.example.umc3_teamproject.domain;

import com.example.umc3_teamproject.Forum.domain.ForumScript;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@SequenceGenerator(
        name = "SEQ_GEN",
        sequenceName = "SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCRIPT_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String type;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean deleted;

    @OneToMany(mappedBy = "script",cascade = CascadeType.ALL)
    List<ScriptParagraph> scriptParagraphs = new ArrayList<>();

    @OneToMany(mappedBy = "script",orphanRemoval = true)
    List<ForumScript> forumScripts = new ArrayList<>();
    //== 연간관계 메서드 ==//
    public void addScriptParagraph(ScriptParagraph scriptParagraph){
        scriptParagraphs.add(scriptParagraph);
        scriptParagraph.setScript(this);
    }

    //== 생성 메서드 ==//
    public void createScript(User user,String title,String type,ScriptParagraph... scriptParagraphs){
        this.user = user;
        this.title = title;
        this.type = type;
        for(ScriptParagraph scriptParagraph: scriptParagraphs){
            this.addScriptParagraph(scriptParagraph);
        }
        this.deleted = false;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    //== 비즈니스 로직==//
    public void addForumScript(ForumScript forumScript){
        forumScripts.add(forumScript);
        forumScript.setScript(this);
    }
    public void changeDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public void setUser(User user){
        this.user = user;
    }

}
