package com.example.umc3_teamproject.domain.item;


import com.example.umc3_teamproject.domain.Member;
import com.fasterxml.jackson.annotation.*;

import lombok.*;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.FetchType.LAZY;

@Builder
@Entity
@Table(name = "script")
@Getter
@Setter
// @SQLDelete(sql = "UPDATE umc3.script SET deleted = true WHERE script_id = ?")   // JPA Soft Delete
@Where(clause = "deleted = false")
@JsonIdentityReference(alwaysAsId = true)
// @JsonIgnoreProperties({"hibernateLazyInitializer"})
@AllArgsConstructor
@NoArgsConstructor
public class Script extends BaseEntity {

    @Id                                              // Primary Key와 연결
    @GeneratedValue(strategy = GenerationType.AUTO)  // 식별자 값을 자동 생성
    @Column(name="scriptId", updatable = false)
    private Long scriptId;

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
    private List<Paragraph> paragraphs;

    @OneToMany(mappedBy = "scriptId", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<RecordScript> record_script;

    @OneToOne(mappedBy = "script",fetch = LAZY,orphanRemoval = true)
    private Memo memo;

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