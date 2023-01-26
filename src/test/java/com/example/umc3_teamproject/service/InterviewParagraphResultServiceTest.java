//package com.example.umc3_teamproject.service;
//
//import com.example.umc3_teamproject.config.resTemplate.ResponsePageTemplate;
//import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
//import com.example.umc3_teamproject.domain.dto.request.CommentRequestDto;
//import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
//import com.example.umc3_teamproject.domain.dto.request.InterviewResultRequestDto;
//import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
//import com.example.umc3_teamproject.domain.dto.response.CommentResponseDto;
//import com.example.umc3_teamproject.domain.dto.response.ForumResponseDto;
//import com.example.umc3_teamproject.domain.dto.response.InterviewResultResponseDto;
//import com.example.umc3_teamproject.domain.item.Script;
//import com.example.umc3_teamproject.dto.SignupReq;
//import com.example.umc3_teamproject.repository.MemberRepository;
//import com.example.umc3_teamproject.repository.ScriptRepository;
//import org.joda.time.Minutes;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class InterviewParagraphResultServiceTest {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private ScriptService scriptService;
//
//    @Autowired
//    private ScriptRepository scriptRepository;
//
//    @Autowired
//    private InterviewParagraphResultService interviewParagraphResultService;
//
//    @Test
//    public void 댓글_페이징_테스트() throws Exception{
//        //given
//        Long userId = memberRepository.createUser(new SignupReq("asldkj@gmail.com", "sldkfj", 1, "KIM", "sldkfj"));
//
//        ScriptRequestDto.Register register = new ScriptRequestDto.Register();
//        register.setTitle("title");
//        register.setUserId(userId);
//        ResponseEntity<?> responseEntity = scriptService.writeScript(register);
//        List<Script> findScripts = scriptRepository.findAll();
//        // where
//        InterviewResultRequestDto.createInterviewResult request = new InterviewResultRequestDto.createInterviewResult();
//        request.setEndTime(LocalDateTime.now().plusMinutes(2));
//        request.setStartTime(LocalDateTime.now());
//        request.setVoiceContent("음 가장 힘들었던 부분은 아무래도 프론트와 백 사이의 소통이었습니다. 프론트는 백에 대해 잘 알지 못하고 백은 프론트에 대해 잘 알지 못하는 상황에서 서로 배려하고, 맞추어나가고 꾸준히 합의점을 맞추어 나가려고 노력하다보니 합의가 되었던 것 같습니다." +
//                " 어 그래서 제가 무조건 열심히 하겠습니다. 음 그래서");
//
//        //then
//        ResponseTemplate<InterviewResultResponseDto.Body> interviewResult = interviewParagraphResultService.createInterviewResult(findScripts.get(0).getScriptId(), request);
//        LocalDateTime startTime = request.getStartTime();
//        LocalDateTime endTime = request.getEndTime();
//
//        System.out.println(startTime.toLocalTime());
//        System.out.println(endTime.toLocalTime());
//
//        int startNanoTime = startTime.getNano();
//        int endNanoTime = endTime.getNano();
//        int endMinusStart = endNanoTime-startNanoTime;
//        double second = ((double) endMinusStart) / 1000000000;
////        System.out.println(startTime);
////        System.out.println(endTime);
////        System.out.println(startNanoTime);
////        System.out.println(endNanoTime);
////        System.out.println(second);
////        System.out.println(ChronoUnit.MINUTES.between(startTime,endTime));
////        System.out.println(ChronoUnit.SECONDS.between(startTime,endTime));
//    }
//
//}
