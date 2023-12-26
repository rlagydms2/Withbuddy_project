package com.lec.spring.withbuddy_project.controller;

import com.lec.spring.withbuddy_project.service.DeshboardPetService;
import com.lec.spring.withbuddy_project.service.DeshboardService;
import com.lec.spring.withbuddy_project.service.DeshboardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @Autowired
    private final DeshboardUserService deshboardUserService;
    @Autowired
    private final DeshboardPetService deshboardPetService;

    @Autowired
    private final DeshboardService deshboardService;

    public AdminPageController(DeshboardUserService deshboardUserService, DeshboardPetService deshboardPetService, DeshboardService deshboardService){
        this.deshboardUserService = deshboardUserService;
        this.deshboardPetService = deshboardPetService;
        this.deshboardService = deshboardService;
        System.out.println("AdminPageController 생성 완료");
    }

    /*유저 관련 대시보드*/
    @GetMapping("/user/deshboard")
    public void deshboard(Model model) {
        model.addAttribute("petcategory",deshboardPetService.typeName());
        model.addAttribute("categorycount",deshboardPetService.petcount());
        model.addAttribute("address",deshboardUserService.address());
        model.addAttribute("usercount",deshboardUserService.usercount());
        model.addAttribute("list",deshboardService.list());
    }
}