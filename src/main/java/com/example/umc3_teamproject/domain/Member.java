package com.example.umc3_teamproject.domain;

import com.example.umc3_teamproject.config.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

import static com.example.umc3_teamproject.domain.Tier.BRONZE;

@Entity
@Getter @Setter
@Table(name = "Member")
public class Member extends BaseTimeEntity {

    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    @Column(name="memberId")
    private Long id;

    @Column(nullable = false, length=50, unique=true)
    private String email;

    @Column(nullable = false, length=100)
    private String pw;

    @Column(nullable = false, length=30)
    private String nickName;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tier tier ;

    @Enumerated(value = EnumType.STRING)
    private LoginType loginType; //일반 로그인 또는 소셜로그인

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Script> scripts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Interview> interviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Report> reports = new ArrayList<>();

    @Builder
    public Member(String email, String pw, String nickName, String imageUrl, Tier tier, LoginType loginType){
        this.email = email;
        this.pw = pw;
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        this.tier = tier;
        this.loginType = loginType;
    }
//    private boolean comments_alarm_permission;
//    private boolean voice_permission;
//    private boolean event_permission;
//    private boolean report_status;

    //닉네임 업데이트
    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    //이미지 업데이트
    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //티어 업데이트

    //일반 계정에서 비밀번호 업데이트

}
