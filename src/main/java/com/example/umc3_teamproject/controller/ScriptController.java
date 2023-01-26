package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ScriptResponseDto;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.ScriptRepository;
import com.example.umc3_teamproject.service.ScriptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @PostMapping("/new")
    public ResponseEntity<?> writeScript(@RequestBody ScriptRequestDto.Register write){
        return scriptService.writeScript(write);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readScriptById(@PathVariable("id") Long id) {

        // 참고 문헌: https://jogeum.net/9
        Optional<Script> optionalProduct=scriptRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Script script1 = optionalProduct.get();
            return scriptResponseDto.success(script1);
        }
        return null;
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> readScriptByUser(@PathVariable("memberId") Long memberId) {
        // @PageableDefault(page=0, size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<Script> scriptList=scriptService.findByMemberId(memberId);

        Map<String, Object> result=new HashMap<>();
        result.put("scripts", scriptList);
        result.put("count", scriptList.size());

        return ResponseEntity.ok().body(result);
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ScriptRequestDto.Update script1) {

        Script script_new= scriptService.updateScript(id, script1.getTitle());
        return scriptResponseDto.success(script_new);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteScript(@PathVariable Long id) {
        return scriptService.remove(id);
    }
}