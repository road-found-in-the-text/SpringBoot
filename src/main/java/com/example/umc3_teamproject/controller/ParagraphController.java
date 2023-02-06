package com.example.umc3_teamproject.controller;
import com.example.umc3_teamproject.domain.dto.request.ParagraphRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ParagraphResponseDto;
import com.example.umc3_teamproject.domain.item.Paragraph;
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
    public ResponseEntity<?> writeParagraph(@Validated ParagraphRequestDto.Register write){
        return paragraphService.writeParagraph(write);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> readScriptByUser(@PathVariable("userId") Long userId) {
        List<Paragraph> paragraphList=null;
        if (userId==null) {
            return null;
        } else {
            paragraphList= paragraphRepository.findByUserId(userId);
            Map<String, Object> result=new HashMap<>();
            result.put("paragraphs", paragraphList);
            result.put("count", paragraphList.size());
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readParagraphById(@PathVariable("id") Long id) {
        Optional<Paragraph> optionalProduct=paragraphRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Paragraph paragraph1 = optionalProduct.get();
            log.info("gather test success");
            return  paragraphResponseDto.success(paragraph1);
        }
        log.info("gather test fail");
        return null;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid  ParagraphRequestDto.Update paragraph1) {
        Paragraph paragraph_new= paragraphService.updateParagraph(id, paragraph1);
        Optional<Paragraph> optionalProduct= paragraphRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Paragraph before_paragraph = optionalProduct.get();
            log.info("gather test success");
            before_paragraph.setContents(paragraph_new.getContents());
            return  paragraphResponseDto.success(before_paragraph);
        }
        return null;
    }
    @DeleteMapping("/delete/{id}")
    public String deleteParagraph(@PathVariable Long id) {
        return paragraphService.remove(id);

    }
}