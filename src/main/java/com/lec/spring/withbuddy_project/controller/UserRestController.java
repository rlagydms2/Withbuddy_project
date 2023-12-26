package com.lec.spring.withbuddy_project.controller;

import com.lec.spring.withbuddy_project.domain.User;
import com.lec.spring.withbuddy_project.service.EmailService;
import com.lec.spring.withbuddy_project.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/user/rest/*")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    //아이디 중복
    @PostMapping("/checkId")
    public boolean checkId(String userId) {
        if (!userService.isExist(userId)) {
            return true;
        } else {
            return false;
        }
    }

    //이메일 중복
    @PostMapping("/email")
    public boolean checkEmail(String email) {
        if (!userService.isExistEmail(email)) {
            return true;
        } else {
            return false;
        }
    }

    //이메일 인증코드 받기
    @PostMapping("/login/mailConfirm")
    public String mailConfirm(String email) throws MessagingException, UnsupportedEncodingException {

        String authCode = emailService.sendEmail(email);
        return authCode;
    }


    // 아이디 찾기 12/24
    @PostMapping("/findUsernameByEmail")
    public ResponseEntity<String> findUsernameByEmail(@RequestBody String email) {
        User user = userService.findUsernameByEmail(email);
        if (user != null) {
            return ResponseEntity.ok("아이디는 " + user.getUserId() + "입니다.");
        } else {
            return ResponseEntity.badRequest().body("해당 이메일로 등록된 사용자가 없습니다.");
        }
    }


    // 비밀번호 찾기 12/24
    @PostMapping("/findPW")
    public ResponseEntity<String> findPW(@RequestBody String email) {
        User user = userService.findUsernameByEmail(email);
        if (user != null) {
            // 새로운 비밀번호 생성
            String newPassword = userService.generateNewPassword();

            // 비밀번호 업데이트
            userService.updatePassword(user, newPassword);

            // 이메일로 새로운 비밀번호 전송
            try {
                emailService.sendNewPasswordEmail(user.getEmail(), newPassword);
            } catch (MessagingException | UnsupportedEncodingException e) {
                return ResponseEntity.badRequest().body("이메일 전송 중 오류가 발생했습니다.");
            }

            return ResponseEntity.ok("새로운 비밀번호가 이메일로 전송되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("해당 이메일로 등록된 사용자가 없습니다.");
        }
    }

}


