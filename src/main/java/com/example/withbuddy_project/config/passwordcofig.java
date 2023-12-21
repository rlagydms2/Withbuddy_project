package com.example.withbuddy_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class passwordcofig {
    //    passwordEncoder 를 bean 으로 IoC 에 등록
    @Bean
    public PasswordEncoder encoder(){
        System.out.println("패스워드 인코더 bean 생성");
        return new BCryptPasswordEncoder();
    }
}
