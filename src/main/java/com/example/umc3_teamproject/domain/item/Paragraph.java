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
@Table(name = "paragraph")
@Getter
@Setter
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityReference(alwaysAsId = true)
// @JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Paragraph extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="paragraphId", updatable = false)
    private Long paragraphId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    // @JsonBackReference
    @JsonIgnore
    private Member memberId;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="scriptId")
    @JsonBackReference
    private Script scriptId;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    @JsonIgnore
    private boolean deleted;


   // @OneToMany(mappedBy = "paragraph", orphanRemoval = true,cascade = CascadeType.ALL)
    // private List<ParagraphImage> paragraphImages = new ArrayList<>();

}