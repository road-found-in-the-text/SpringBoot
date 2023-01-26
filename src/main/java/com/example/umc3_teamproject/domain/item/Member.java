package com.example.umc3_teamproject.domain.item;

import com.example.umc3_teamproject.config.BaseTimeEntity;
import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.Forum;
import com.example.umc3_teamproject.domain.item.Script;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Entity
@NoArgsConstructor @AllArgsConstructor
@Table(name="Member")
@JsonIdentityReference(alwaysAsId = true)
public class Member extends BaseTimeEntity {

    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    @Column(name="memberId")
    private Long id;

    @Column()
    private String socialId;
    @Column(nullable = false) //unique true
    private String email;
    @Column() //nullable = false
    private String pw;
    @Column(nullable = false, length=30)
    private String nickName;
    @Column(nullable = true)
    private String imageUrl;
    @Column(nullable = false)
    @ColumnDefault("0")
    private int tier ;

    private int loginType; //일반 로그인 또는 소셜로그인

    private int memberStatus;

    private int blockStatus;

    @OneToMany(mappedBy = "userId", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Script> scripts ;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Interview> interviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Forum> forums = new ArrayList<>();

    @Builder
    public Member(String socialId) {
        this.socialId = socialId;
    }


    @Builder
    public Member(Long id,String email, String socialId,String pw, String nickName, String imageUrl, int tier, int loginType, int memberStatus, int blockStatus){
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
}
