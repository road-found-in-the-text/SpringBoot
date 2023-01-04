package com.example.umc3_teamproject.Forum.domain;


import com.example.umc3_teamproject.domain.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@SequenceGenerator(
        name = "Forum_SEQ_GEN",
        sequenceName = "Forum_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Forum_SEQ_GEN")
    @Column(name = "FORUM_ID")
    private Long id;

    private String writer;                      // 글쓴이
    private String title;                       // 제목
    private String content;                     // 글
    private int like_num;                           // 좋아요 수
    private LocalDateTime createTime;           // 생성 시간
    private LocalDateTime updateTime;           // 수정 시간
    private boolean deleted_status;             // 삭제 유무
    private boolean script_status;              // 대본 유무
    private boolean image_status;               // 이미지 유무
    private boolean video_status;               // 비디오 유무
    private boolean interview_status;           // 인터뷰 유무
    private boolean report_status;              // 신고 유무

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;                          // User 테이블과 양방향 관계

    @OneToMany(mappedBy = "forum",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ForumScript> forumScripts = new ArrayList<>();

//    private List<Interview> interviews = new ArrayList<>();

    @OneToMany(mappedBy = "forum",orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 댓글
//
    @OneToMany(mappedBy = "forum",orphanRemoval = true)
    private List<ForumImage> forumImages = new ArrayList<>();

    // video는 어떻게 해야 할지 고민을 해봐야 겠다.

    //== 생성 메서드 ==//

    // 일단은 script만 생각했다 interview는 script 다 한 다음에 생각하자.
    public void createForum(User user,String title, String content) {
        this.writer = user.getName();
        this.title = title;
        this.content = content;
        this.user = user;
        this.script_status = false;
        this.report_status = false;
        this.image_status = false;
        this.video_status = false;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.deleted_status = false;
    }

    public void updateForum(String title, String content,List<Script> scripts) {
        this.writer = user.getName();
        this.title = title;
        this.content = content;
        if(!scripts.isEmpty()){
            this.script_status = true;
            for(int i = 0 ; i < scripts.size();i++){
                ForumScript forumScript = new ForumScript();
                forumScript.setScript(scripts.get(i));
                scripts.get(i).addForumScript(forumScript);
                addForumScript(forumScript);
            }
        }else{
            this.script_status = false;
        }
        this.updateTime = LocalDateTime.now();
    }

    //== 연간관계 메서드 ==//
    public void addForumScript(ForumScript forumScript){
        forumScripts.add(forumScript);
        forumScript.setForum(this);
        forumScript.setDeleted_status(false);
    }

    public void deleteForumScript(ForumScript forumScript){

    }

    //== 비즈니스 로직==//
    public void changeDeleted(boolean deleted){
        this.deleted_status = deleted;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setScript_status_true(){
        this.script_status = true;
    }
}
