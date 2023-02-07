package com.example.umc3_teamproject.controller;
import com.example.umc3_teamproject.domain.dto.request.ParagraphRequestDto;
import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ParagraphResponseDto;
import com.example.umc3_teamproject.domain.item.Paragraph;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.ParagraphRepository;
import com.example.umc3_teamproject.service.ParagraphService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/script/paragraph")
@RequiredArgsConstructor
@Api(tags = {"Paragraph Api"})
public class ParagraphController {

    private final ParagraphService paragraphService;
    private final ParagraphRepository paragraphRepository;
    private final ParagraphResponseDto paragraphResponseDto;

    @PostMapping("/new")
    public ResponseEntity<?> writeParagraph(@RequestBody ParagraphRequestDto.Register write ){
        return paragraphService.writeParagraph(write);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readParagraphById(@PathVariable("id") Long id) {
        Optional<Paragraph> optionalProduct=paragraphRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Paragraph paragraph1 = optionalProduct.get();
            return  paragraphResponseDto.success(paragraph1);
        }
        return null;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody  ParagraphRequestDto.Update paragraph1) {
        Paragraph paragraph_new= paragraphService.updateParagraph(id, paragraph1.getTitle());
        return paragraphResponseDto.success(paragraph_new);


    }

}