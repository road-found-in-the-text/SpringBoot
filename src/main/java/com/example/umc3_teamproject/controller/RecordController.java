package com.example.umc3_teamproject.controller;
import com.example.umc3_teamproject.domain.dto.request.RecordScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.response.RecordScriptResponseDto;
import com.example.umc3_teamproject.domain.dto.response.ScriptResponseDto;
import com.example.umc3_teamproject.domain.item.RecordScript;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.RecordRespository;
import com.example.umc3_teamproject.repository.ScriptRepository;
import com.example.umc3_teamproject.service.RecordScriptService;
import com.example.umc3_teamproject.service.ScriptService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController      // Json 형태로 객체 데이터를 반환 (@Controller + @ResponseBody)
@RequestMapping("/script")
@RequiredArgsConstructor
@Api(tags = {"Record Api"})
public class RecordController {

    private final RecordScriptService recordscriptService;
    private final RecordRespository recordscriptRepository;

    private final RecordScriptResponseDto recordscriptResponseDto;


    @PostMapping("/addRecord")
    public ResponseEntity<?> recordScript(@RequestBody RecordScriptRequestDto.Register record_script ){

        return recordscriptService.recordScript(record_script);
    }



    @GetMapping("/record/{id}")
    public ResponseEntity<?> readRecordScriptById(@PathVariable("id") Long id) {


        Optional<RecordScript> optionalProduct=recordscriptRepository.findById(id);
        if (optionalProduct.isPresent()) {
            RecordScript record_script1 = optionalProduct.get();
            return recordscriptResponseDto.success(record_script1);
        }
        return null;
    }

}