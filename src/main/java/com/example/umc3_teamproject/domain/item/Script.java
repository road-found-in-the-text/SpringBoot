package com.example.umc3_teamproject.domain.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member memberId;


    @Column
    private String title;

    @Column
    private boolean deleted;


    @OneToMany(mappedBy = "scriptId", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    // private List<Paragraph> paragraphList = new ArrayList<>();
    private List<Paragraph> paragraphList;

    public void addParagraph(Paragraph paragraph) {
        paragraphList.add(paragraph);
    }

    public void deleteParagraph(Paragraph paragraph) {
        paragraphList.remove(paragraph);
    }


}