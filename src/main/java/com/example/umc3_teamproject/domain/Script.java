package com.example.umc3_teamproject.domain;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "script")
@Getter
@Setter
// @SQLDelete(sql = "UPDATE umc3.script SET deleted = true WHERE script_id = ?")   // JPA Soft Delete
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
public class Script extends BaseEntity {

    @Id                   // Primary Key와 연결
    @GeneratedValue(strategy = GenerationType.AUTO)       // 식별자 값을 자동 생성
    @Column(name="scriptId", updatable = false)
    private Long scriptId;

    @Column(name="userId", updatable = false)
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

    @PreRemove
    public void deleteScript(){
        this.deleted = false;
    }

    @OneToMany(mappedBy = "script")
    private List<ForumScript> forumScripts = new ArrayList<>();
}