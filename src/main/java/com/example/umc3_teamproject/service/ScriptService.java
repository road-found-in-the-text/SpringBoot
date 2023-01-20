package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ScriptResponseDto;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ScriptService {

    @Autowired
    private final ScriptRepository scriptRepository;
    private final ScriptResponseDto scriptResponse;

    /*
    @Transactional(readOnly = true)
    public List<Script> findAll() {
        return scriptRepository.findAll();
    }
     */

    public ResponseEntity<?> writeScript(ScriptRequestDto.Register script1) {

        Script script=Script.builder()
                .userId(script1.getUserId())
                .title(script1.getTitle())
                // .type(script1.getType())
                .deleted(false)
                .build();
        scriptRepository.save(script);

        return scriptResponse.success(script);
    }

    public Script updateScript(Long id, ScriptRequestDto.Update script1) {

        Optional<Script> optionalProduct=scriptRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Script before_script = optionalProduct.get();

            Script updatedScript=new Script();
            updatedScript.setScriptId(id);
            updatedScript.setUserId(before_script.getUserId());
            updatedScript.setCreatedDate(before_script.getCreatedDate());
            updatedScript.setTitle(script1.getTitle());
            // updatedScript.setType(script1.getType());
            scriptRepository.save(updatedScript);

            return updatedScript;

        }
        return null;
    }

    public String remove(Long id){
        Optional<Script> optionalProduct=scriptRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Script before_script = optionalProduct.get();

            Script deletedScript=new Script();
            deletedScript.setScriptId(id);
            deletedScript.setUserId(before_script.getUserId());
            deletedScript.setCreatedDate(before_script.getCreatedDate());
            deletedScript.setTitle(before_script.getTitle());
            // deletedScript.setType(before_script.getType());
            deletedScript.setDeleted(true);
            scriptRepository.save(deletedScript);

            return id+" deleted success";
        }
        return null;
    }

}