package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.Script;
import com.example.umc3_teamproject.domain.ScriptParagraph;
import com.example.umc3_teamproject.repository.ScriptInterviewSearch;
import com.example.umc3_teamproject.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScriptService {

    private final ScriptRepository scriptRepository;

    // 대본 생성
    @Transactional
    public Long saveScript(Script script){
        scriptRepository.save(script);
        return script.getId();
    }

    //대본 하나 조회
    public Script findOne(Long scriptId){
        return scriptRepository.findOne(scriptId);
    }

    //대본 모두 조회
    public List<Script> findAll(ScriptInterviewSearch scriptInterviewSearch){
        return scriptRepository.findAllByUserId(scriptInterviewSearch);
    }

    //대본 paragraph 생성
    @Transactional
    public Long saveScriptParagraph(ScriptParagraph scriptParagraph){
        scriptRepository.saveParagraph(scriptParagraph);
        return scriptParagraph.getId();
    }
}
