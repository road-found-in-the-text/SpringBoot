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
    public ResponseEntity<?> readGather(@PathVariable("id") Long id) {

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

}
