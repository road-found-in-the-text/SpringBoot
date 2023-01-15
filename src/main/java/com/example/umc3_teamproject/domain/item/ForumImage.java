package com.example.umc3_teamproject.domain.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "deleted_status = false")
public class ForumImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ForumImage_Id")
    private Long id;

    @Column(length = 4000)
    private String imageUrl;

    @Column(length = 4000)
    private String storeFilename;

    private LocalDateTime createTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    private boolean deleted_status;

    @Builder
    public ForumImage(String imageUrl, String storeFilename, Forum forum) {
        this.imageUrl = imageUrl;
        this.storeFilename = storeFilename;
        this.forum = forum;
        this.createTime = LocalDateTime.now();
    }

    public void deleteImage(){
        this.deleted_status = true;
    }

}
