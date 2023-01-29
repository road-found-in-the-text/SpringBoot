//package com.example.umc3_teamproject.service;
//
//import com.example.umc3_teamproject.config.resTemplate.ResponsePageTemplate;
//import com.example.umc3_teamproject.config.resTemplate.ResponseTemplate;
//import com.example.umc3_teamproject.domain.Member;
//import com.example.umc3_teamproject.domain.dto.request.CommentRequestDto;
//import com.example.umc3_teamproject.domain.dto.request.ForumRequestDto;
//import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
//import com.example.umc3_teamproject.domain.dto.response.CommentResponseDto;
//import com.example.umc3_teamproject.domain.dto.response.ForumResponseDto;
//import com.example.umc3_teamproject.dto.SignupReq;
//import com.example.umc3_teamproject.repository.MemberRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class CommentServiceTest {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private ScriptService scriptService;
//
//    @Autowired
//    private ForumService forumService;
//
//    @Autowired
//    private CommentService commentService;
//
//    @Test
//    public void 댓글_페이징_테스트() throws Exception{
//        //given
//        Long userId = memberRepository.createUser(new SignupReq("asldkj@gmail.com", "sldkfj", 1, "KIM", "sldkfj"));
//
//        ScriptRequestDto.Register register = new ScriptRequestDto.Register();
//        register.setTitle("title");
//        register.setUserId(userId);
//        scriptService.writeScript(register);
//
//        ResponseTemplate<ForumResponseDto.ForumDataToGetResult> forum = forumService.createForum(userId, new ForumRequestDto.createForumRequest());
//        //when
//
//        for(int i = 0 ; i <= 200 ; i++){
//            commentService.createComment(forum.getData().getForumId(), new CommentRequestDto.createCommentRequest(userId,"comment"));
//        }
//        Pageable pageable = new Pageable() {
//            @Override
//            public int getPageNumber() {
//                return 0;
//            }
//
//            @Override
//            public int getPageSize() {
//                return 0;
//            }
//
//            @Override
//            public long getOffset() {
//                return 0;
//            }
//
//            @Override
//            public Sort getSort() {
//                return null;
//            }
//
//            @Override
//            public Pageable next() {
//                return null;
//            }
//
//            @Override
//            public Pageable previousOrFirst() {
//                return null;
//            }
//
//            @Override
//            public Pageable first() {
//                return null;
//            }
//
//            @Override
//            public Pageable withPage(int pageNumber) {
//                return null;
//            }
//
//            @Override
//            public boolean hasPrevious() {
//                return false;
//            }
//        };
//        //then
//        ResponsePageTemplate<List<CommentResponseDto.Body>> allByForumId = commentService.getAllByForumId(forum.getData().getForumId(),pageable);
//
//        System.out.println(allByForumId.getData().size());
//    }
//
//}
