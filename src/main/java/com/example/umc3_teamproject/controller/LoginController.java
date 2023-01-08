package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @ResponseBody
    @PostMapping("/login")
    public ResponseTemplate<?> logIn(@RequestBody)


}
