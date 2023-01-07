package com.example.umc3_teamproject.controller;


import com.example.umc3_teamproject.domain.Member;
import com.example.umc3_teamproject.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/members/create")
    public CreateMemberResponse create(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();

        member.setEmail(request.getEmail());
        member.setPw(request.getPw());
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/members/create/{id}")
    public UpdateMemberResponse updateMemberName(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }


    @Data
    static class CreateMemberRequest{
        private String email;
        private String pw;
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private long id;

        public CreateMemberResponse(Long id){
            this.id = id;
        }
    }

    //로그인

}
