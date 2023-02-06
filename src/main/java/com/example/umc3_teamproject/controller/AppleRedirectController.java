package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.dto.AuthReq;
import com.example.umc3_teamproject.dto.AuthRes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@Controller
public class AppleRedirectController {

    @RequestMapping("/oauth2/idpresponse")
    public String index() {
        return "index";
    }

}
