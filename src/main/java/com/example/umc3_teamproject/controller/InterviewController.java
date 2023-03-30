package com.example.umc3_teamproject.controller;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.domain.dto.request.InterviewRequestDto;
import com.example.umc3_teamproject.domain.dto.response.InterviewResponseDto;
import com.example.umc3_teamproject.domain.item.Interview;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.InterviewRepository;
import com.example.umc3_teamproject.service.InterviewService;
import com.example.umc3_teamproject.service.JwtService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController      // Json 형태로 객체 데이터를 반환 (@Controller + @ResponseBody)
@RequestMapping("/interview")
@Api(tags = {"Interview Api"})
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;
    private final InterviewRepository interviewRepository;
    private final InterviewResponseDto interviewResponseDto;
    private final JwtService jwtService;

    @PostMapping("/new")
    public ResponseEntity<?> writeInterview(@Validated InterviewRequestDto.Register write){
        return interviewService.writeInterview(write);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readScriptById(@PathVariable("id") Long id) {

        // 참고 문헌: https://jogeum.net/9
        Optional<Interview> optionalProduct=interviewRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Interview interview1 = optionalProduct.get();
            log.info("gather test success");
            return interviewResponseDto.success(interview1);
        }

        log.info("gather test fail");
        return null;
    }

    @GetMapping("/user/me")
    public ResponseEntity<?> readScriptByUser() throws IOException, ResponseException {
        Long userId = jwtService.getmemberId();

        List<Interview> interviewList=null;

        if (userId==null) {
            return null;
        } else {
            interviewList=interviewRepository.findByUserId(userId);

            Map<String, Object> result=new HashMap<>();
            result.put("scripts", interviewList);
            result.put("count", interviewList.size());

            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getScriptListAll() {

        // 참고문헌: https://goddaehee.tistory.com/209
        List<Interview> interview1 = interviewRepository.findAll();

        Map<String, Object> result=new HashMap<>();
        result.put("scripts", interview1);
        result.put("count", interview1.size());

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid InterviewRequestDto.Update interview1) {

        Interview script_new= interviewService.updateInterview(id, interview1);

        Optional<Interview> optionalProduct=interviewRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Interview before_interview = optionalProduct.get();
            log.info("gather test success");

            before_interview.setTitle(script_new.getTitle());
            before_interview.setType(script_new.getType());

            return interviewResponseDto.success(before_interview);
        }

        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteScript(@PathVariable Long id) {
        return interviewService.removeInterview(id);
    }

}
