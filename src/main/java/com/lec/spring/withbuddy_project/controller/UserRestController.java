package com.lec.spring.withbuddy_project.controller;

import com.lec.spring.withbuddy_project.service.EmailService;
import com.lec.spring.withbuddy_project.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user/rest/*")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/checkId")
    public boolean checkId(String userId) {
        if (!userService.isExist(userId)) {
            return true;
        }else{
            return false ;
        }
    }

    @PostMapping("/email")
    public boolean checkEmail(String email) {
        if (!userService.isExistEmail(email)) {
            return true;
        }else{
            return false ;
        }
    }

    @PostMapping("/login/mailConfirm")
    public String mailConfirm(String email) throws MessagingException, UnsupportedEncodingException {

        String authCode = emailService.sendEmail(email);
        return authCode;
    }








}