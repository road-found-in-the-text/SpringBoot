package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ScriptResponseDto;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScriptService {

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
                .type(script1.getType())
                .build();
        scriptRepository.save(script);

        return scriptResponse.success(script);
    }

    public Script updateScript(ScriptRequestDto.Update script1) {

        Script script_new=Script.builder()
                .title(script1.getTitle())
                .type(script1.getType())
                .build();
        scriptRepository.save(script_new);

        return script_new;
    }






    /* search */
    /*
    @Transactional
    public List<Script> search(String keyword) {
        List<Script> scriptList = scriptRepository.findByTitleContaining(keyword);
        return scriptList;
    }
     */

    @Transactional
    public void saveItem(Script script) {
        scriptRepository.save(script);
    }


    /*
    @Transactional
    public List<Script> findUserScript(Long userId) {
        return scriptRepository.findByUserId(userId);
    }
     */

    /*
    @Transactional
    public List<Script> findScriptOfUser(Long userId, Long id) {
        return scriptRepository.findByScriptId(userId, id);
    }
     */
    /*
    @Transactional(readOnly = true)
    public Script getUserById(Long userId) {
        return scriptRepository.findByUserId(userId)
                .orElseThrow(()-> new IllegalArgumentException("User id를 확인해주세요."));
    }

    @Transactional(readOnly = true)
    public Script getScriptById(Long userId, Long id) {
        return scriptRepository.findByScriptId(userId, id)
                .orElseThrow(()-> new IllegalArgumentException("Script id를 확인해주세요."));
    }
     */

    // 변경 감지에 의해 data를 변경
    /*
    @Transactional
    public void updateItem(Long scriptId, Script scriptItem) {
        
        Script findItem= scriptRepository.findOne(scriptId);
        findItem.setTitle(scriptItem.getTitle());
        findItem.setType(scriptItem.getType());
    }
    */

    /*
    public List<Script> findScripts() {
        return scriptRepository.findAll();
    }
     */

    /*
    public Script findOne(Long scriptId) {
        return scriptRepository.findOne(scriptId);
    }
     */
}
