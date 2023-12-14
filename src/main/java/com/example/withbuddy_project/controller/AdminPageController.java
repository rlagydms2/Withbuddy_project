package com.example.withbuddy_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminPageController {

    @GetMapping("/adminpage")
    public String adminpage(){
        return "adminpage";
    }
}