package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.dto.request.ScriptRequestDto;
import com.example.umc3_teamproject.domain.item.Script;
import com.example.umc3_teamproject.repository.ScriptRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional    // test를 한 후 끝나면 다 rollback 해버린다.
class ScriptServiceTest {

    @Autowired
    ScriptService scriptService;

    @Autowired
    ScriptRepository scriptRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 글_아이디로_글_찾기() {
        Optional<Script> name=scriptRepository.findById(1L);

        if (name.isPresent()) {
            Script found_script = name.get();
            assertEquals("title1", found_script.getTitle());
        }
    }

    @Test
    public void 사용자_아이디로_글_찾기() {
        List<Script> scriptList=scriptRepository.findByUserId(1L);

        assertEquals(scriptList.size(), 2);
    }

    @Test
    void writeScript() throws Exception {

        // given
        ScriptRequestDto.Register script=new ScriptRequestDto.Register();
        script.setTitle("test1");
        script.setUserId(1L);
        script.setType("test type 1");

        // when
        scriptService.writeScript(script);

        // then
        em.flush();
        // assertEquals(script, scriptRepository.findById(savedId));
    }

    @Test
    void updateScript() throws Exception {
        // given
        ScriptRequestDto.Update script=new ScriptRequestDto.Update();
        script.setTitle("test2");
        script.setType("test type 2");

        // when
        scriptService.updateScript(1L, script);

        // then
        em.flush();
    }

    @Test
    void remove() throws Exception {

        Script script=new Script();
        script.setTitle("title1");
        script.setUserId(1L);
        script.setType("type1");
        script.setDeleted(true);

        scriptService.remove(1L);
    }
}