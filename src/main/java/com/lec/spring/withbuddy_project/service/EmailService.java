package com.lec.spring.withbuddy_project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    String setFrom; //email-config에 설정한 자신의 이메일 주소(보내는 사람)

    //메일 양식 작성
    public MimeMessage createEmailForm(String email, String randomCode) throws MessagingException, UnsupportedEncodingException {

//        createCode(); //인증 코드 생성
        String toEmail = email; //받는 사람
        String title = "이메일 회원가입 인증 번호"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(randomCode);

        return message;
    }

    //실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        String randomCode = UUID.randomUUID().toString().substring(0,5);

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail,randomCode);
        //실제 메일 전송
        emailSender.send(emailForm);
        return randomCode; //인증 코드 반환
    }
}
