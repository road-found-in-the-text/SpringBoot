package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.domain.dto.GetResult;
import com.example.umc3_teamproject.domain.dto.request.ScriptIdsToRequest;
import com.example.umc3_teamproject.domain.dto.request.createForumRequest;
import com.example.umc3_teamproject.domain.dto.response.createForumResponse;
import com.example.umc3_teamproject.domain.item.Forum;
import com.example.umc3_teamproject.domain.item.Member;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.ForumRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;


@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class ForumServiceTest {


    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private ForumService forumService;

    @Autowired
    private EntityManager em;
    
    // delete는 제대로 잘 된다.
    @Test
    public void forum_삭제() throws Exception{
        //given
        Response response = createUser_Script_ForumScript();
        Member member = response.member;
        Forum forum = response.forum;
        List<ScriptIdsToRequest> scriptIdsToRequests = response.scriptIdsToRequests;

        createForumResponse forum_create_test = forumService.createForum(member.getId(), new createForumRequest(forum.getTitle(), forum.getContent(), scriptIdsToRequests, null));
        //when

        String s = forumService.deleteForum(forum_create_test.getForum_id());
        //then
        Forum forum1 = forumRepository.findOne(forum_create_test.getForum_id());
        assertThat(forum1).isEqualTo(null);
    }
    
    // script id가 없다면 forumScript를 생성할 때 오류가 발생한다.
    // ForumScript를 생성할 때 Script 아이디를 받아와서 조회한 후 넣는데 조회가 불가능하니 오류가 발생
    @Test
    public void forum_생성() throws Exception{
        //given
        Response response = createUser_Script_ForumScript();
        Forum forum = response.forum;
        List<ScriptIdsToRequest> scriptIdsToRequests = response.scriptIdsToRequests;
        //when
        createForumResponse forum_create_test = forumService.createForum(response.member.getId(), new createForumRequest(forum.getTitle(), forum.getContent(), scriptIdsToRequests, null));
        //then
        Forum forum1 = forumRepository.findOne(forum_create_test.getForum_id());
        System.out.println(forum1.getId());
    }
    
    @Test
    public void forum_수정() throws Exception{
        //given
        Response response = createUser_Script_ForumScript();
        Member member = response.member;
        Forum forum = response.forum;
        List<ScriptIdsToRequest> scriptIdsToRequests = response.scriptIdsToRequests;

        createForumResponse forum_create_test = forumService.createForum(response.member.getId(), new createForumRequest(forum.getTitle(), forum.getContent(), scriptIdsToRequests, null));
        //when
        GetResult getResult = forumService.updateForumResult(member.getId(), forum_create_test.getForum_id(), new createForumRequest(
                "update1", "updateContent1", null, null)
        );
        //then
        Forum update_forum = forumService.findOne(forum_create_test.getForum_id());

        List<ScriptIdsToRequest> scriptIdsToRequests1 = new ArrayList<>();
        assertThat(update_forum.getTitle()).isEqualTo("update1");
        assertThat(update_forum.getContent()).isEqualTo("updateContent1");
        assertThat(update_forum.getForumScripts()).isEqualTo(scriptIdsToRequests1);
    }

    @Test
    public void forum_하나_조회() throws Exception{
        //given
        Response response = createUser_Script_ForumScript();
        Member member = response.member;
        Forum forum = response.forum;
        List<ScriptIdsToRequest> scriptIdsToRequests = response.scriptIdsToRequests;

        createForumResponse forum_create_test = forumService.createForum(response.member.getId(), new createForumRequest(forum.getTitle(), forum.getContent(), scriptIdsToRequests, null));
        //when
        forumService.getForumByForumId(2L);
        //then
        fail("forumId가 존재하지 않아.");
    }

    @Transactional
    public Response createUser_Script_ForumScript(){
        Member member = new Member();
        member.setNickName("김현재");
        member.setId(1L);
        member.setPw("password");
        member.setEmail("@naver.com");
        em.persist(member);

        // script 생성
        Script script = new Script();
        script.setType("script");
        script.setUserId(member.getId());
        script.setTitle("script1");
        em.persist(script);

        Forum forum = new Forum();
        forum.createForum(member,"title","content");
        ScriptIdsToRequest scriptIdsToRequest1 = new ScriptIdsToRequest(script.getScriptId());
        List<ScriptIdsToRequest> scriptIdsToRequests = new ArrayList<>();
        scriptIdsToRequests.add(scriptIdsToRequest1);
        return new Response(member,forum,scriptIdsToRequests);
    }

    static class Response{
        private Member member;
        private Forum forum;
        private List<ScriptIdsToRequest> scriptIdsToRequests;

        public Response(Member member, Forum forum, List<ScriptIdsToRequest> scriptIdsToRequests) {
            this.member = member;
            this.forum = forum;
            this.scriptIdsToRequests = scriptIdsToRequests;
        }
    }
}
