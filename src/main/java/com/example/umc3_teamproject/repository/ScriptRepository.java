package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.domain.Script;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

// script 하나 찾기
// script 모두 찾기
// scriptParagraph id에 해당하는 scriptParagraph을 찾기
// Script update (ScriptParagraph이 update가 되면 자동 update) - domain에 Script클래스에 구현함.
// Scripte deleted 변수 변경해서 삭제된 척 해주기
@Repository
public interface ScriptRepository extends JpaRepository<Script,Long> {

}



