package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.Script;
import com.example.umc3_teamproject.domain.User;
import com.example.umc3_teamproject.repository.ScriptInterviewSearch;
import com.example.umc3_teamproject.service.ScriptService;
import com.example.umc3_teamproject.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Script API"})
public class ScriptAPI {

    private final UserService userService;
    private final ScriptService scriptService;

    @PostMapping("/scripts/{userid}/new")
    public createScriptResponse createScript(@PathVariable("userid") Long id, @RequestBody @Validated createScriptRequest request){
        User findUser = userService.findOne(id);
        Script script = new Script();
        script.createScript(findUser,request.getTitle(),request.getType());
        Long scriptId = scriptService.saveScript(script);
        return new createScriptResponse(scriptId,script.getTitle(),script.getType());
    }

    @GetMapping("/scripts/{userid}")
    public Result searchScript(@PathVariable("userid") Long id){

        List<Script> scripts = scriptService.findAll(new ScriptInterviewSearch(id,false));
        List<ScriptDto> scriptDtos = scripts.stream().map(
                s -> new ScriptDto(s.getUser().getId(),s.getId(),s.getTitle(),s.getType()))
                .collect(Collectors.toList());

        return new Result(scriptDtos.size(),scriptDtos);
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class createScriptRequest {
        private String title;
        private String type;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class createScriptResponse{
        private Long id;
        private String title;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class ScriptDto{
        private Long userId;
        private Long scriptId;
        private String title;
        private String type;

    }
}
