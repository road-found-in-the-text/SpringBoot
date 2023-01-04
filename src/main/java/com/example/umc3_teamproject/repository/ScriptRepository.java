package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Script;
import com.example.umc3_teamproject.domain.ScriptParagraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

// script 하나 찾기
// script 모두 찾기
// scriptParagraph id에 해당하는 scriptParagraph을 찾기
// Script update (ScriptParagraph이 update가 되면 자동 update) - domain에 Script클래스에 구현함.
// Scripte deleted 변수 변경해서 삭제된 척 해주기
@Repository
@RequiredArgsConstructor
public class ScriptRepository {
    private final EntityManager em;

    // script 저장.
    public void save(Script script){
        if(script.getId() == null){
            em.persist(script);
        }else{
            em.merge(script);
        }
    }

    // script paragraph 저장.
    public void saveParagraph(ScriptParagraph scriptParagraph){
        Script findScript = findOne(scriptParagraph.getScript().getId());
        findScript.addScriptParagraph(scriptParagraph);
        if(scriptParagraph.getId() == null){
            em.persist(findScript);
        }else{
            em.merge(findScript);
        }
    }

    // script 하나 찾기
    public Script findOne(Long id){
        return em.find(Script.class,id);
    }

    // 모든 script 찾기
    public List<Script> findAllByUserId(ScriptInterviewSearch scriptSearch){
        String jpql = "select s From Script s join s.user u";
        boolean isFirstCondition = true;

        // deleted 되었는지 확인
        if(scriptSearch.isDeleted() != true){
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " s.deleted = :deleted";
        }

        // userId로 검색
        if (StringUtils.hasText(String.valueOf(scriptSearch.getUserId()))) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " u.id = :id";
        }
        TypedQuery<Script> query = em.createQuery(jpql, Script.class) .setMaxResults(100); //최대 1000건
        if (scriptSearch.isDeleted() != true) {
            query = query.setParameter("deleted", scriptSearch.isDeleted());
        }
        if (StringUtils.hasText(String.valueOf(scriptSearch.getUserId()))) {
            query = query.setParameter("id", scriptSearch.getUserId());
        }
        return query.getResultList();
    }

    // Script 삭제 - 이건 ScriptService에 가야 할 것 같다.
    public void deleteScript (Long id){
        Script findOne = findOne(id);
        findOne.changeDeleted(true);
        em.persist(findOne);
    }
}



