package com.example.umc3_teamproject.repository;

import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.dto.SignupReq;
import com.example.umc3_teamproject.dto.SignupRes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static com.example.umc3_teamproject.domain.Tier.BRONZE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MemberRepositoryTest {

    private MemberRepository memberRepository;
    private SignupReq signupReq;

    @Before
    public void setUp() {
        memberRepository = Mockito.mock(MemberRepository.class);
        signupReq = new SignupReq("test@test.com","passwo123!!rd",BRONZE,"test","http://test.com/image.jpg");
    }

    @Test
    public void 멤버생성테스트() throws ResponseException {
        // given
        Long expectedId1 = 1L;
        Long expectedId2 = 2L;

        // when을 사용하여 mock 안에서의 test를 수행한다.
        when(memberRepository.createMember(signupReq)).thenReturn(expectedId1).thenReturn(expectedId2);

        // when
        Long actualId1 = memberRepository.createMember(signupReq);
        Long actualId2 = memberRepository.createMember(signupReq);

        // then
        assertEquals(expectedId1, actualId1);
        assertEquals(expectedId2, actualId2);
    }
}
