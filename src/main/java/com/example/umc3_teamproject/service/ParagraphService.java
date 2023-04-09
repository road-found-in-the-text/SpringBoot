package com.example.umc3_teamproject.service;
import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.dto.request.ParagraphRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ParagraphResponseDto;
import com.example.umc3_teamproject.domain.item.Paragraph;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.MemberRepository;
import com.example.umc3_teamproject.repository.ParagraphRepository;
import com.example.umc3_teamproject.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@Service
@RequiredArgsConstructor

public class ParagraphService {
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final ScriptRepository scriptRepository;
    @Autowired
    private final ParagraphRepository paragraphRepository;
    private final ParagraphResponseDto paragraphResponse;
    private final ScriptService scriptService;

    private final EntityManager em;

    public ResponseEntity<?> writeParagraph(ParagraphRequestDto.Register paragraph1) {

        Member paragraph_member=memberRepository.getUser(paragraph1.getMemberId());
        Script paragraph_script = scriptRepository.getById(paragraph1.getScriptId());

        Paragraph paragraph=Paragraph.builder()
                .memberId(paragraph_member)
                .title(paragraph1.getTitle())
                .scriptId(paragraph_script)
                .contents(paragraph1.getContents())
                .deleted(false)
                .build();

        scriptService.addParagraph(paragraph.getScriptId().getScriptId(), paragraph);
        paragraphRepository.save(paragraph);
        return paragraphResponse.success(paragraph);
    }

    @Transactional
    public Paragraph updateParagraph(Long id, String title) {
        Paragraph toChangeParagraph=em.find(Paragraph.class, id);
        toChangeParagraph.setTitle(title);
        em.merge(toChangeParagraph);
        return toChangeParagraph;
    }

    @Transactional
    public void saveItem(Paragraph paragraph) {
        paragraphRepository.save(paragraph);
    }

    public List<Paragraph> findByScriptId(Long scriptId){
        return  em.createQuery("select s from Paragraph s where s.scriptId.id= :id", Paragraph.class)
                .setParameter("id", scriptId)
                .getResultList();
    };
}
