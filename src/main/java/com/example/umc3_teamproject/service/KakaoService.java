package com.example.umc3_teamproject.service;



import com.example.umc3_teamproject.domain.item.LoginType;
import com.example.umc3_teamproject.domain.item.Member;
import com.example.umc3_teamproject.dto.KakaoRes;
import com.example.umc3_teamproject.exception.TokenValidFailedException;
import com.example.umc3_teamproject.repository.ProxyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoService implements ProxyRepository {

    @Autowired
    private final WebClient webClient;

    @Override
    public Member getMemberData(String accessToken) {
        KakaoRes kakaoRes = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException("Social Access Token is unauthorized")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException("Internal Server Error")))
                .bodyToMono(KakaoRes.class)
                .block();

        System.out.println(kakaoRes.getId());


        return Member.builder()
                .socialId(String.valueOf(kakaoRes.getId()))
                .loginType(1) //LoginType.KAKAO
                .build();
    }
}
