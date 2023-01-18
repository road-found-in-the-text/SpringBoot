



/*

package com.example.umc3_teamproject.domain.item;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ParagraphImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ParagraphImage_Id")
    private Long id;

    @Column(length = 4000)
    private String imageUrl;

    @Column(length = 4000)
    private String storeFilename;

    private LocalDateTime createTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "paragraphId")
    private Paragraph paragraph;

    @Builder
    public ParagraphImage(String imageUrl, String storeFilename, Paragraph paragraph) {
        this.imageUrl = imageUrl;
        this.storeFilename = storeFilename;
        this.paragraph = paragraph;
        this.createTime = LocalDateTime.now();
    }

}


 */