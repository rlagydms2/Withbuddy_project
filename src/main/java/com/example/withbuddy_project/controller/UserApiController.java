package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.domain.dto.AddressUserDto;
import com.example.withbuddy_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/home")
    public AddressUserDto buttonList(@RequestParam(name = "addressName") String addressName) {
        AddressUserDto addressUserDto = userService.selectAddress(addressName);
        return addressUserDto;
    }

}
