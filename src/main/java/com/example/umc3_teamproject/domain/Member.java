package com.example.umc3_teamproject.domain;

import com.example.umc3_teamproject.config.BaseTimeEntity;
import com.example.umc3_teamproject.domain.item.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String nickName;
    @Column(nullable = true)
    @JsonIgnore
    private String imageUrl;
    @Column
    private Tier tier ;

    @Column
    private LoginType loginType; //일반 로그인 또는 소셜로그인

    @Column
    private int memberStatus;

    @Column
    private int blockStatus;


    @OneToMany(mappedBy = "memberId", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Script> scripts = new ArrayList<>();

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Interview> interviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Forum> forums = new ArrayList<>();


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
    public Member(Long id,String email, String socialId,String pw, String nickName, String imageUrl, Tier tier, LoginType loginType, int memberStatus, int blockStatus){
        this.id = id;
        this.email = email;
        this.socialId = socialId;
        this.pw = pw;
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        this.tier = tier;
        this.loginType = loginType;
        this.memberStatus = memberStatus;
        this.blockStatus = blockStatus;
    }

    public void createMember(String email,String pw, String nickName, String imageUrl ){
        this.email = email;
        this.pw = pw;
        this.nickName=nickName;
        this.imageUrl = imageUrl;
        this.socialId = "0";
        this.memberStatus=1;
        this.tier= BRONZE;
        this.loginType = LoginType.DEFAULT;
        this.blockStatus=0;
    }
}