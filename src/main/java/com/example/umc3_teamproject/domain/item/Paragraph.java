package com.example.umc3_teamproject.domain.item;
import lombok.*;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "paragraph")
@Getter
@Setter
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
public class Paragraph extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)


    @Column(name="paragraphId", updatable = false)
    private Long paragraphId;

    @Column(name="scriptId", updatable = false)
    private Long scriptId;

    @Column(name="userId", updatable = false)
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