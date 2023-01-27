package com.example.umc3_teamproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
// https://velog.io/@leesomyoung/SpringBoot-JavaMailSender-%EC%9E%90%EB%8F%99-%EC%A3%BC%EC%9E%85-%EB%B6%88%EA%B0%80-%EC%98%A4%EB%A5%98
@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("road.found.in.the.text@gmail.com");
        // javaMailSender.setPassword("text1234!!");
        // javaMailSender.setPassword("qevpwjsimawtrnjt");
        javaMailSender.setPassword("zfqmmgkejlbneomz");
        javaMailSender.setPort(587);
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.starttls.trust","smtp.gmail.com");	// 네이버의 경우 stmp.naver.com 변경
        properties.setProperty("mail.smtp.starttls.enable","true");	            // starttls <-> ssl로 변경도 확인(*starttls 와 ssl은 추후 다루도록 하겠습니다.)
        return properties;
    }
}