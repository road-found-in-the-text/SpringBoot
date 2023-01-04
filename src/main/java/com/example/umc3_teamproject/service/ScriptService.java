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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ScriptService {

    @Autowired
    private final ScriptRepository scriptRepository;
    private final ScriptResponseDto scriptResponse;

    private EntityManager entityManager;   // 변경 저장 위해 사용

    private static List<Script> scripts = new ArrayList<>();

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
            updatedScript.setType(script1.getType());
            scriptRepository.save(updatedScript);

            return updatedScript;

        }

        return null;

        /*
        Optional<Script> optionalProduct=scriptRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Script update_script = optionalProduct.get();

            update_script.setTitle(script1.getTitle());
            update_script.setType(script1.getType());

            // 새로운 것은 persist, 수정은 merge
            // entityManager.merge(update_script);

            return update_script;
        }

         */

        // return null;
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
            deletedScript.setType(before_script.getType());
            deletedScript.setDeleted(true);
            scriptRepository.save(deletedScript);

            return id+" deleted success";

        }

        return null;
    }

    /*
    public Script deleteScriptById(Long id) {

        Optional<Script> optionalProduct=scriptRepository.findById(id);
        // Script delete_script = optionalProduct.get();
        optionalProduct.delete();
        return optionalProduct.orElse(null);

        Iterator<Script> iterator = scripts.iterator();
        while(iterator.hasNext()) {
            Script script = iterator.next();
            if(Objects.equals(script.getScriptId(), id)) {
                iterator.remove();
                return script;
            }
        }

    }
    */






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
