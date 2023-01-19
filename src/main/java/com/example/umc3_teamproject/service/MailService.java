package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.domain.dto.request.MailRequestDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "glegil.text@gmail.com";

    @Transactional
    public String reportMailSend(MailRequestDto.MailDto mailDto){
        try{
            SimpleMailMessage  message = new SimpleMailMessage();
            message.setFrom(FROM_ADDRESS);
            message.setTo(mailDto.getAddress());
            message.setSubject(mailDto.getTitle());
            message.setText(mailDto.getMessage());
            javaMailSender.send(message);
            System.out.println("메세지 전송을 성공하였습니다.");
            return "이메일 성공";
        }catch (MailException e){
            e.printStackTrace();
            return "실패";
        }

    }



    private MimeMessage createMessage(String ePw,String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.setFrom(FROM_ADDRESS);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("글길 회원가입 이메일 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 글길 입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용

        return message;
    }

    public static String createKey() {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<7; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }

    public String sendSimpleMessage(String to)throws Exception {
        String ePw = createKey();
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(ePw,to);
        try{//예외처리
            javaMailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }

}