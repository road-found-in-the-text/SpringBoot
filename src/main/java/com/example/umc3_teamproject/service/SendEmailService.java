package com.example.umc3_teamproject.service;

import com.example.umc3_teamproject.config.AES128;
import com.example.umc3_teamproject.config.EncryptionUtils;
import com.example.umc3_teamproject.config.SecurityConfig;
import com.example.umc3_teamproject.config.resTemplate.ResponseException;
import com.example.umc3_teamproject.dto.MailDto;
import com.example.umc3_teamproject.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.umc3_teamproject.config.resTemplate.ResponseTemplateStatus.INVALID_JWT;

@Service
@AllArgsConstructor
public class SendEmailService {

    @Autowired
    MemberRepository userRepository;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "road.found.in.the.text@gmail.com";



    public MailDto createMailAndChangePassword(String userEmail, String userName) throws ResponseException {
        String str = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(userEmail);
        dto.setTitle(userName+"님의 HOTTHINK 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. HOTTHINK 임시비밀번호 안내 관련 이메일 입니다." + "[" + userName + "]" +"님의 임시 비밀번호는 "
                + str + " 입니다.");
        updatePassword(str,userEmail);
        mailSend(dto);
        return dto;
    }

    public void updatePassword(String password_input,String userEmail) throws ResponseException {
        // String pw = EncryptionUtils.encryptMD5(str);
        Optional<Long> id = userRepository.findUserIdByEmail(userEmail);
        String pwd;
        try {
            pwd =  new AES128(SecurityConfig.USER_INFO_PASSWORD_KEY).encrypt(password_input); // 암호화코드
            if (id.isPresent())
                userRepository.updatePassword(id.get(),pwd);

        } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new ResponseException(INVALID_JWT);
        }


    }


    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    public void mailSend(MailDto mailDto){
        System.out.println("이멜 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(SendEmailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
}
