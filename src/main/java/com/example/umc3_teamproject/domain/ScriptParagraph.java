package com.example.umc3_teamproject.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@SequenceGenerator(
        name = "SEQ_GEN",
        sequenceName = "SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScriptParagraph {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_GEN")
    @Column(name = "SCRIPT_PARAGRAPH_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "script_id")
    private Script script;

    private String cotents;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean deleted;

    public ScriptParagraph(Script script, String cotents,LocalDateTime createTime) {
        this.script = script;
        this.cotents = cotents;
        this.deleted = false;
        this.createTime = createTime;
        this.updateTime = LocalDateTime.now();
    }

    //== 생성 메서드 ==//
    public static ScriptParagraph createScriptParagraph(Script script, String contents){
        ScriptParagraph scriptParagraph = new ScriptParagraph(script,contents,LocalDateTime.now());
        return scriptParagraph;
    }

    // 이미지는 어떻게 해야 하는지 알아봐야겠다.

    //== 비즈니스 로직==//
    public void changeDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public void setScript(Script script){
        this.script = script;
    }
}
