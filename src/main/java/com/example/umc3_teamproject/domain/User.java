package com.example.umc3_teamproject.domain;

import com.example.umc3_teamproject.Forum.domain.Forum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(
        name = "USER_SEQ_GEN",
        sequenceName = "USER_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_SEQ_GEN")
    @Column(name = "USER_ID")
    private Long id;
    private String name;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean alarm_permission;
    private boolean voice_permission;
    private boolean event_permission;

    @OneToMany(mappedBy = "user")
    private List<Script> scripts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Interview> interviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Forum> forums = new ArrayList<>();

    //==연간관계 메서드==//

    public void addScript(Script script){
        scripts.add(script);
        script.setUser(this);
    }
    public void addInterview(Interview interview){
        interviews.add(interview);
        interview.setUser(this);
    }
    public void addForum(Forum forum){
        forums.add(forum);
        forum.setUser(this);
    }

    //== 생성 메서드 ==//
    public void createUser(String name, String password, String nickname) {
        this.name = name;
        this.password = password;
        this.nickname = nickname;
    }
}
