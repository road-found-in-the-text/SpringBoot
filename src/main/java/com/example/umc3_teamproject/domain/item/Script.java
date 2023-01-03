package com.example.umc3_teamproject.domain.item;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "script")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Script extends BaseEntity {

    @Id                   // Primary Key와 연결
    @GeneratedValue(strategy = GenerationType.AUTO)       // 식별자 값을 자동 생성
    @Column(name="scriptId")
    private Long scriptId;

    @Column(name="userId")
    private Long userId;

    // @ManyToOne(fetch = LAZY)
    // @JoinColumn(name = "user_id")
    // private User user_id;

    @Column
    private String title;

    @Column
    private String type;

    @Column
    private boolean deleted;

}
