package com.example.umc3_teamproject.domain.item;

import com.example.umc3_teamproject.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Report extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long id;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "reporter_id")
    private Member member;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "nested_comment_id")
    private NestedComment nestedComment;


    public void createReport(String title, String content,ReportType reportType,Member member) {
        this.title = title;
        this.content = content;
        this.reportType = reportType;
        this.setCreatedDate(LocalDateTime.now());
        this.setModifiedDate(LocalDateTime.now());
    }

    public void setForum(Forum forum) {
        this.forum = forum;
        this.comment = null;
        this.nestedComment = null;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
        this.forum = null;
        this.nestedComment = null;
    }

    public void setNestedComment(NestedComment nestedComment) {
        this.nestedComment = nestedComment;
        this.forum = null;
        this.comment = null;
    }


}
