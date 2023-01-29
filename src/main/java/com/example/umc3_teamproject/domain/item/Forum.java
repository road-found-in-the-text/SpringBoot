package com.example.umc3_teamproject.domain.item;


import com.example.umc3_teamproject.domain.Member;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "deleted_status = false")
public class Forum extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FORUM_ID")
    private Long id;

    private String writer;                      // 글쓴이
    private String title;                       // 제목
    private String content;                     // 글
    private int like_num;                           // 좋아요 수
    private boolean deleted_status;             // 삭제 유무
    private boolean script_status;              // 대본 유무
    private boolean image_video_status;
    private boolean interview_status;           // 인터뷰 유무
    private boolean report_status;              // 신고 유무

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Member member;                          // User 테이블과 양방향 관계

    @OneToMany(mappedBy = "forum",orphanRemoval = true)
    private List<ForumScript> forumScripts = new ArrayList<>();

    @OneToMany(mappedBy = "forum",orphanRemoval = true)
    private List<ForumInterview> forumInterviews = new ArrayList<>();

    @OneToMany(mappedBy = "forum",orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); // 댓글
    //
    @OneToMany(mappedBy = "forum",orphanRemoval = true)
    private List<ForumImage> forumImages = new ArrayList<>();

    //== 생성 메서드 ==//

    // 일단은 script만 생각했다 interview는 script 다 한 다음에 생각하자.
    public void createForum(Member member,String title, String content) {
        this.writer = member.getNickName();
        this.title = title;
        this.content = content;
        this.member = member;
        this.script_status = false;
        this.report_status = false;
        this.image_video_status = false;
        this.interview_status = false;
        this.deleted_status = false;
        this.setCreatedDate(LocalDateTime.now());
        this.setModifiedDate(LocalDateTime.now());
    }

    public void updateForum(String title, String content) {
        this.title = title;
        this.content = content;
        this.setModifiedDate(LocalDateTime.now());
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

    public void setScript_status_false(){this.script_status = false;}

    public void setInterview_status_true(){
        this.interview_status = true;
    }

    public void setInterview_status_false(){this.interview_status = false;}

    public void setImage_video_status_true(){this.image_video_status = true;}
    public void setImage_video_status_false(){this.image_video_status = false;}
    public void likePlus(){this.like_num++;}

    public void likeMinus(){
        if(this.like_num == 0){
            like_num = 0;
        }else{
            like_num--;
        }
    }
}
