package com.example.umc3_teamproject.service;
import com.example.umc3_teamproject.domain.dto.request.ParagraphRequestDto;
import com.example.umc3_teamproject.domain.dto.response.ParagraphResponseDto;
import com.example.umc3_teamproject.domain.item.Paragraph;
import com.example.umc3_teamproject.repository.ParagraphRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ParagraphService {
    @Autowired
    private final ParagraphRepository paragraphRepository;
    private final ParagraphResponseDto paragraphResponse;
    public ResponseEntity<?> writeParagraph(ParagraphRequestDto.Register paragraph1) {

        Paragraph paragraph=Paragraph.builder()
                .userId(paragraph1.getUserId())
                .scriptId(paragraph1.getScriptId())
                .contents(paragraph1.getContents())
                .deleted(false)
                .build();
        paragraphRepository.save(paragraph);
        return paragraphResponse.success(paragraph);
    }




    public Paragraph updateParagraph(Long id, ParagraphRequestDto.Update paragraph1) {
        Optional<Paragraph> optionalProduct=paragraphRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Paragraph before_paragraph = optionalProduct.get();
            Paragraph updateParagraph=new Paragraph();
            updateParagraph.setParagraphId(id);
            updateParagraph.setScriptId(id);
            updateParagraph.setUserId(before_paragraph.getUserId());
            updateParagraph.setCreatedDate(before_paragraph.getCreatedDate());
            updateParagraph.setContents(paragraph1.getContents());
            paragraphRepository.save(updateParagraph);
            return updateParagraph;
        }
        return null;
    }

    public String remove(Long id){
        Optional<Paragraph> optionalProduct=paragraphRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Paragraph before_paragraph = optionalProduct.get();
            Paragraph deletedParagraph=new Paragraph();
            deletedParagraph.setParagraphId(id);
            deletedParagraph.setScriptId(id);
            deletedParagraph.setUserId(before_paragraph.getUserId());
            deletedParagraph.setCreatedDate(before_paragraph.getCreatedDate());
            deletedParagraph.setContents(before_paragraph.getContents());
            deletedParagraph.setDeleted(true);
            paragraphRepository.save(deletedParagraph);
            return id+" deleted success";
        }
        return null;
    }
    @Transactional
    public void saveItem(Paragraph paragraph) {

        paragraphRepository.save(paragraph);


    }
}
