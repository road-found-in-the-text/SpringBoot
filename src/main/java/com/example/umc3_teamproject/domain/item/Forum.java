package com.example.umc3_teamproject.domain.item;


import com.example.umc3_teamproject.domain.item.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
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
public class Forum extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Forum_SEQ_GEN")
    @Column(name = "FORUM_ID")
    private Long id;

    private String writer;                      // 글쓴이
    private String title;                       // 제목
    private String content;                     // 글
    private int like_num;                           // 좋아요 수
    private boolean deleted_status;             // 삭제 유무
    private boolean script_status;              // 대본 유무
    private boolean image_status;               // 이미지 유무
    private boolean video_status;               // 비디오 유무
    private boolean interview_status;           // 인터뷰 유무
    private boolean report_status;              // 신고 유무

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Member member;                          // User 테이블과 양방향 관계

    @OneToMany(mappedBy = "forum",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ForumScript> forumScripts = new ArrayList<>();

//    private List<Interview> interviews = new ArrayList<>();

//    @OneToMany(mappedBy = "forum",orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>(); // 댓글
    //
    @OneToMany(mappedBy = "forum",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<ForumImage> forumImages = new ArrayList<>();

    // video는 어떻게 해야 할지 고민을 해봐야 겠다.

    //== 생성 메서드 ==//

    // 일단은 script만 생각했다 interview는 script 다 한 다음에 생각하자.
    public void createForum(Member member,String title, String content) {
        this.writer = member.getNickName();
        this.title = title;
        this.content = content;
        this.member = member;
        this.script_status = false;
        this.report_status = false;
        this.image_status = false;
        this.video_status = false;
        this.deleted_status = false;
    }

    public void updateForum(String title, String content) {
        this.writer = member.getNickName();
        this.title = title;
        this.content = content;
        this.forumScripts = new ArrayList<>();

    }

    //== 비즈니스 로직==//
    public void changeDeleted(boolean deleted){
        this.deleted_status = deleted;
    }

    public void setUser(Member member){
        this.member = member;
    }

    public void setScript_status_true(){
        this.script_status = true;
    }

    public void likePlus(){this.like_num++;}

    public void likeMinus(){
        if(this.like_num == 0){
            like_num = 0;
        }else{
            like_num--;
        }
    }
}
