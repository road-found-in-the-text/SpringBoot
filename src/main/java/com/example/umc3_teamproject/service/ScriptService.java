package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ScriptResponseDto;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScriptService {
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final ScriptRepository scriptRepository;
    private final ScriptResponseDto scriptResponse;
    private final EntityManager em;


    public ResponseEntity<?> writeScript(ScriptRequestDto.Register script1) {

        Member script_member=memberRepository.getUser(script1.getMemberId());

        Script script=Script.builder()
                .memberId(script_member)
                .title(script1.getTitle())
                // .type(script1.getType())
                .deleted(false)
                .build();
        scriptRepository.save(script);

        return scriptResponse.firstSuccess(script);
    }

    @Transactional
    public Script updateScript(Long id, String title) {
        Script toChangeScript=em.find(Script.class, id);
        toChangeScript.setTitle(title);
        em.merge(toChangeScript);
        return toChangeScript;
    }

    /*
    @Transactional
    public List<Script> search(Long memberId) {
        return scriptRepository.findByMemberId(memberId);
    }
     */

    public List<Script> findByMemberId(Long memberId){
        return  em.createQuery("select s from Script s where s.memberId.id= :id", Script.class)
                .setParameter("id", memberId)
                .getResultList();
    };

    @Transactional
    public Script addParagraph(Long id, Long[] paragraphIdList){
        Script addParagraphScript=em.find(Script.class, id);

        for (int i=0; i< paragraphIdList.length ; i++) {

            Paragraph new_paragraph=em.find(Paragraph.class, paragraphIdList[i]);
            addParagraphScript.addParagraph(new_paragraph);
        }
        em.merge(addParagraphScript);
        return addParagraphScript;
    }

    @Transactional
    public Script deleteParagraph(Long id, Long[] paragraphIdList){
        Script deleteParagraphScript=em.find(Script.class, id);

        for (int i=0; i< paragraphIdList.length ; i++) {

            Paragraph before_paragraph=em.find(Paragraph.class, paragraphIdList[i]);
            deleteParagraphScript.addParagraph(before_paragraph);
        }
        em.merge(deleteParagraphScript);
        return deleteParagraphScript;
    }
    @Transactional
    public String remove(Long id){
        Script toRemoveScript=em.find(Script.class, id);
        toRemoveScript.setDeleted(true);
        em.merge(toRemoveScript);

        return "script id ["+id+"] deleted success";
    }

    // 이전 ver
    /*
    public Script updateScript2(Long id, String title) {
        // 이 방법 사용하는 이유: 그냥 merge하면 createdate랑 memberid가 X 넘어감
        Optional<Script> optionalProduct=scriptRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Script before_script = optionalProduct.get();
            Script updatedScript=new Script();
            updatedScript.setScriptId(id);
            updatedScript.setMemberId(before_script.getMemberId());
            updatedScript.setCreatedDate(before_script.getCreatedDate());
            updatedScript.setTitle(title);
            updatedScript.setParagraphList(before_script.getParagraphList());
            scriptRepository.save(updatedScript);
            return updatedScript;
        }
        return null;
    }
     */
}