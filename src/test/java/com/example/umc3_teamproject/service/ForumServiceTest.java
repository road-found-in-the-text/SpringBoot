package com.example.umc3_teamproject.service;


import com.example.umc3_teamproject.domain.Dto.GetResult;
import com.example.umc3_teamproject.domain.Dto.request.ScriptIdsToRequest;
import com.example.umc3_teamproject.domain.Dto.request.createForumRequest;
import com.example.umc3_teamproject.domain.Dto.response.createForumResponse;
import com.example.umc3_teamproject.domain.Forum;
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
        User user = response.user;
        Forum forum = response.forum;
        List<ScriptIdsToRequest> scriptIdsToRequests = response.scriptIdsToRequests;

        createForumResponse forum_create_test = forumService.createForum(user.getId(), new createForumRequest(forum.getTitle(), forum.getContent(), scriptIdsToRequests, null));
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
        createForumResponse forum_create_test = forumService.createForum(response.user.getId(), new createForumRequest(forum.getTitle(), forum.getContent(), scriptIdsToRequests, null));
        //then
        Forum forum1 = forumRepository.findOne(forum_create_test.getForum_id());
        System.out.println(forum1.getId());
    }
    
    @Test
    public void forum_수정() throws Exception{
        //given
        Response response = createUser_Script_ForumScript();
        User user = response.user;
        Forum forum = response.forum;
        List<ScriptIdsToRequest> scriptIdsToRequests = response.scriptIdsToRequests;

        createForumResponse forum_create_test = forumService.createForum(response.user.getId(), new createForumRequest(forum.getTitle(), forum.getContent(), scriptIdsToRequests, null));
        //when
        GetResult getResult = forumService.updateForumResult(user.getId(), forum_create_test.getForum_id(), new createForumRequest(
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
        User user = response.user;
        Forum forum = response.forum;
        List<ScriptIdsToRequest> scriptIdsToRequests = response.scriptIdsToRequests;

        createForumResponse forum_create_test = forumService.createForum(response.user.getId(), new createForumRequest(forum.getTitle(), forum.getContent(), scriptIdsToRequests, null));
        //when
        forumService.getForumByForumId(2L);
        //then
        fail("forumId가 존재하지 않아.");
    }

    @Transactional
    public Response createUser_Script_ForumScript(){
        User user = new User();
        user.createUser("김현재","password","nickname");
        em.persist(user);

        // script 생성
        Script script = new Script();
        script.createScript(user,"script1","type");
        em.persist(script);

        Forum forum = new Forum();
        forum.createForum(user,"title","content");
        ScriptIdsToRequest scriptIdsToRequest1 = new ScriptIdsToRequest(script.getId());
        List<ScriptIdsToRequest> scriptIdsToRequests = new ArrayList<>();
        scriptIdsToRequests.add(scriptIdsToRequest1);
        return new Response(user,forum,scriptIdsToRequests);
    }

    static class Response{
        private User user;
        private Forum forum;
        private List<ScriptIdsToRequest> scriptIdsToRequests;

        public Response(User user, Forum forum, List<ScriptIdsToRequest> scriptIdsToRequests) {
            this.user = user;
            this.forum = forum;
            this.scriptIdsToRequests = scriptIdsToRequests;
        }
    }
}
