package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
public class HomeController {
    private final UserService userService;

    @GetMapping("/")
    public String Home() {

        return "/home";
    }
}
