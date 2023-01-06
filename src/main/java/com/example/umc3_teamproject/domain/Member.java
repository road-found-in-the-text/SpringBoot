package com.example.umc3_teamproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="memberId")
    private Long id;
    private String pw;
    private String name;
//    @Enumerated(EnumType.STRING)
//    private Tier Tier;
//    private boolean comments_alarm_permission;
//    private boolean voice_permission;
//    private boolean event_permission;
//    private boolean report_status;

}
