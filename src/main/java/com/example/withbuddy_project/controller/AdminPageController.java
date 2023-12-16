package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.service.DeshboardPetService;
import com.example.withbuddy_project.service.DeshboardUserService;
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

    public AdminPageController(DeshboardUserService deshboardUserService, DeshboardPetService deshboardPetService){
        this.deshboardUserService = deshboardUserService;
        this.deshboardPetService = deshboardPetService;
        System.out.println("AdminPageController 생성 완료");
    }

    /*유저 관련 대시보드*/
    @GetMapping("/adminpage/deshboardUser")
    public void deshboarduser(Model model) {
        model.addAttribute("address",deshboardUserService.address());
        model.addAttribute("usercount",deshboardUserService.usercount());
    }

    @GetMapping("/adminpage/deshboardPet")
    public void deshboardpet(Model model){
        model.addAttribute("petcategory",deshboardPetService.typeName());
        model.addAttribute("categorycount",deshboardPetService.petcount());
    }

}