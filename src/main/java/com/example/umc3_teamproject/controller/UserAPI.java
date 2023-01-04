package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.domain.User;
import com.example.umc3_teamproject.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
@Api(tags = {"회원가입 API"})
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;

    @PostMapping("/new")
    @ApiOperation(value = "회원가입", response = User.class)
    public CreateUserResponse createUser(@RequestBody @Validated CreateUserRequest request) {
        User user = new User();
        user.createUser(request.getName(),request.getPassword(),request.getNickname());

        Long userId = userService.save(user);
        return new CreateUserResponse(userId,request.getName(),request.getPassword(),request.getNickname());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateUserRequest {
        private String name;
        private String password;
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CreateUserResponse {
        private Long id;
        private String name;
        private String password;
        private String nickname;
    }

    @GetMapping("/new")
    public Result searchUser(){
        List<User> users = userService.findAll();
        List<UserDto> userDtos = users.stream()
                .map(u -> new UserDto(u.getId(),u.getName(),u.getPassword(),u.getNickname()))
                .collect(Collectors.toList());
        return new Result(userDtos.size(),userDtos);
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
    static class UserDto{
        private Long id;
        private String name;
        private String password;
        private String nickname;
    }
}
