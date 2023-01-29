package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.Record;
import com.example.umc3_teamproject.repository.RecordRespository;
import io.swagger.models.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class RecordController {
    private final RecordRespository recordRespository;
    @GetMapping("/")
    public String index(){
        // List<Record> records =  recordRespository.findAll();
        //model.addAttribute("records", records);
        return "records";
    }

    @PostMapping("/addRecord")
    public String addRecord(@RequestParam("record") Float record){
        Record record1 = new  Record();
        record1.setRecord(record);
        recordRespository.save(record1);
        return "redirect:/";
    }
}