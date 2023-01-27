package com.example.umc3_teamproject.domain.item;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    // @Column(name="scriptId", updatable = false)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="scriptId")
    @JsonManagedReference
    private Script scriptId;

    @Column(name="userId", updatable = false)
    @JsonManagedReference
    private Long userId;

    @Column
    private String contents;

    @Column
    private boolean deleted;

    @PreRemove
    public void deleteParagraph(){
        this.deleted = false;
    }


   // @OneToMany(mappedBy = "paragraph", orphanRemoval = true,cascade = CascadeType.ALL)
    // private List<ParagraphImage> paragraphImages = new ArrayList<>();

}