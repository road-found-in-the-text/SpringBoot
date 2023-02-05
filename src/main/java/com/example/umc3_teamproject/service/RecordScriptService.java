package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.domain.dto.request.RecordScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.response.RecordScriptResponseDto;
import com.example.umc3_teamproject.domain.dto.response.ScriptResponseDto;
import com.example.umc3_teamproject.domain.item.Paragraph;
import com.example.umc3_teamproject.domain.item.RecordScript;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.MemberRepository;
import com.example.umc3_teamproject.repository.RecordRespository;
import com.example.umc3_teamproject.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordScriptService {
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final ScriptRepository scriptRepository;
    @Autowired
    private final RecordRespository recordscriptRepository;
    private final RecordScriptResponseDto recordscriptResponse;


    public ResponseEntity<?> recordScript(RecordScriptRequestDto.Register record_script1) {

        Member script_member = memberRepository.getUser(record_script1.getMemberId());
        Script record1_script = scriptRepository.getById(record_script1.getScriptId());


        RecordScript record_script= RecordScript.builder()
                .memberId(script_member)
                .scriptId(record1_script)
                .record(record_script1.getRecord())
                .build();
        recordscriptRepository.save(record_script);
        return recordscriptResponse.success(record_script);
    }
    @Transactional
    public void saveItem(RecordScript recordScript) {

        recordscriptRepository.save(recordScript);


    }



}