package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.domain.dto.AddressUserDto;
import com.example.withbuddy_project.service.MapService;
import com.example.withbuddy_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

//    @PostMapping("/api/home")
//    public AddressUserDto buttonList(@RequestParam(name = "addressName") String addressName) {
//        AddressUserDto addressUserDto = userService.selectAddress(addressName);
//        return addressUserDto;
//    }
@GetMapping("/api/user")
public List<User> userList(Long id) {
    List<User> list = userService.findWithoutMe(id);
    return list;
}

    @GetMapping("/api/userProfile/{userId}")
    public User userProfile(@PathVariable Long userId) {
        return userService.findById(userId);
    }
//    @GetMapping("/api/user")
//    public List<User> userList(Long id,Long code) {
//        List<User> list = userService.findWithoutMe(id,code);
//        return list;
//    }

    @PostMapping("/api/dmList")
    public List<User> dmListUser(@RequestParam(name = "loginId") Long loginId) {
        return userService.findUsername(loginId);
    }

}
