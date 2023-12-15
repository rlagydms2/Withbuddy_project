package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.service.DeshboardUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    private DeshboardUserService deshboardService;

    public AdminPageController(){
        System.out.printf("AdminPageController 생성 완료");
    }

    @GetMapping("/adminpage")
    public String adminpage(){
        return "/adminpage/deshboardUser";
    }


    /*유저 관련 대시보드*/
    @GetMapping("/adminpage/deshboardUser")
    public void listAddress(){

    }

}