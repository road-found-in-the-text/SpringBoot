package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ScriptResponseDto;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.ScriptRepository;
import com.example.umc3_teamproject.service.ScriptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController      // Json 형태로 객체 데이터를 반환 (@Controller + @ResponseBody)
@RequestMapping("/script")
@RequiredArgsConstructor
public class ScriptController {

    private final ScriptService scriptService;
    private final ScriptRepository scriptRepository;
    private final ScriptResponseDto scriptResponseDto;

    @PostMapping("/new1")
    public ResponseEntity<?> writeScript(@Validated ScriptRequestDto.Register write){
        // List<ScriptDto> result = productRepository.search(condition);
        // System.out.println(result);
        // return new ResponseEntity<List<ScriptDto>>(result, HttpStatus.OK);

        return scriptService.writeScript(write);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readScriptById(@PathVariable("id") Long id) {

        // 참고 문헌: https://jogeum.net/9
        Optional<Script> optionalProduct=scriptRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Script script1 = optionalProduct.get();
            log.info("gather test success");
            return scriptResponseDto.success(script1);
        }

        log.info("gather test fail");
        return null;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> readScriptByUser(@PathVariable("userId") Long userId) {

        // 참고 문헌: https://jogeum.net/9
        Optional<Script> optionalProduct=scriptRepository.findByUserId(userId);
        if (optionalProduct.isPresent()) {
            Script script1 = optionalProduct.get();
            log.info("gather test success");
            return scriptResponseDto.success(script1);
        }

        log.info("gather test fail");
        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getScriptListAll() {

        // 참고문헌: https://goddaehee.tistory.com/209
        List<Script> scripts = scriptRepository.findAll();

        Map<String, Object> result=new HashMap<>();
        result.put("scripts", scripts);
        result.put("count", scripts.size());

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid ScriptRequestDto.Update script1) {

        Script script_new= scriptService.updateScript(id, script1);

        Optional<Script> optionalProduct=scriptRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Script before_script = optionalProduct.get();
            log.info("gather test success");

            before_script.setTitle(script_new.getTitle());
            before_script.setType(script_new.getType());

            return scriptResponseDto.success(before_script);
        }

        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteScript(@PathVariable Long id) {

        // Script script= scriptRepository.findById(id);
        // String result=scriptService.remove(id);
        // script.deleteScript();

        return scriptService.remove(id);
        /*
        if (script==null) {
            throw new ScriptNotFoundException(String.format("ID[%s] not found", id));
        }
         */
    }
}
