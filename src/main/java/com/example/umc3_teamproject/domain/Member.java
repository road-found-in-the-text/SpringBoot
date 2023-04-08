package com.example.umc3_teamproject.domain;

import com.example.umc3_teamproject.config.BaseTimeEntity;
import com.example.umc3_teamproject.domain.item.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.umc3_teamproject.domain.Tier.BRONZE;

@Getter @Setter @Entity
@NoArgsConstructor @AllArgsConstructor
@Table(name="Member")
@JsonIdentityReference(alwaysAsId = true)
public class Member extends BaseTimeEntity {

    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    @Column(name="memberId")
    private Long id;

    @Column
    private String socialId;
    @Column //unique true
    private String email;
    @Column
    @JsonIgnore
    private String pw;
    @Column(length=30)
    private String nickName;
    @Column(nullable = true)
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Tier tier ;

    @Enumerated(EnumType.STRING)
    private LoginType loginType; //일반 로그인 또는 소셜로그인

    @Column(nullable = true)
    private String introduction; //한 줄 소개

    @Column
    private int memberStatus;

    @Column
    private boolean blockStatus;


    @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Script> scripts ;

    @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Paragraph> paragraphs ;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Interview> interviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Comment> comments ;

    @OneToMany(mappedBy = "member", fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Forum> forums ;


    @OneToMany(mappedBy = "memberId", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<RecordScript> record_script;

    @OneToMany(mappedBy = "memberId", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<RecordInterview> record_interview;


    @Builder
    public Member(String socialId) {
        this.socialId = socialId;
    }



    @Builder
    public Member(Long id,String email, String socialId,String introduction,String pw, String nickName, String imageUrl, Tier tier, LoginType loginType, int memberStatus, boolean blockStatus){
        this.id = id;
        this.email = email;
        this.socialId = socialId;
        this.introduction = introduction;
        this.pw = pw;
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        this.tier = tier;
        this.loginType = loginType;
        this.memberStatus = memberStatus;
        this.blockStatus = blockStatus;
    }
    @Builder
    public void createMember(String email,String pw, String nickName, String imageUrl ){
        this.email = email;
        this.pw = pw;
        this.introduction = "안녕하세요.";
        this.nickName=nickName;
        this.imageUrl = imageUrl;
        this.socialId = "0";
        this.memberStatus=1;
        this.tier= BRONZE;
        this.loginType = LoginType.DEFAULT;
        this.blockStatus=false;
    }
}