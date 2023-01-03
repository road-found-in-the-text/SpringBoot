package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.service.ScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController      // Json 형태로 객체 데이터를 반환 (@Controller + @ResponseBody)
@RequestMapping("/script")
@RequiredArgsConstructor
public class ScriptController {

    private final ScriptService scriptService;

    @GetMapping("")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(scriptService.findAll(), HttpStatus.OK); // 200
    }

    @PostMapping("/new1")
    public ResponseEntity<?> writeScript(@Validated ScriptRequestDto.Register write){
        // List<ScriptDto> result = productRepository.search(condition);
        // System.out.println(result);
        // return new ResponseEntity<List<ScriptDto>>(result, HttpStatus.OK);

        return scriptService.writeScript(write);
    }

}
