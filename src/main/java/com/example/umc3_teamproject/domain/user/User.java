package com.example.umc3_teamproject.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name="userId")
    private Long id;
    private String pw;
    private String username;
    @Enumerated(EnumType.STRING)
    private com.example.umc3_teamproject.domain.user.Tier Tier;
    private boolean comments_alarm_permission;
    private boolean voice_permission;
    private boolean event_permission;
    private boolean report_status;

}
