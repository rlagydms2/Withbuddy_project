package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserServiceImpl userServiceImpl;

//    @PostMapping("/api/home")
//    public AddressUserDto buttonList(@RequestParam(name = "addressName") String addressName) {
//        AddressUserDto addressUserDto = userService.selectAddress(addressName);
//        return addressUserDto;
//    }
@GetMapping("/api/user")
public List<User> userList(Long id) {
    List<User> list = userServiceImpl.findWithoutMe(id); // 나빼고 다 찾기 추후 같은 지역에 사는 사람들만 나오게할 것
    return list;
}

    @GetMapping("/api/userProfile/{userId}")
    public User userProfile(@PathVariable Long userId) {
        return userServiceImpl.findById(userId); // list에 나온 user의 정보를 찾아온걸 html에 전달
    }
//    @GetMapping("/api/user")
//    public List<User> userList(Long id,Long code) {
//        List<User> list = userService.findWithoutMe(id,code);
//        return list;
//    }

    @PostMapping("/api/dmList")
    public List<User> dmListUser(@RequestParam(name = "loginId") Long loginId) {
        return userServiceImpl.findDm(loginId);     // dmList에 렌더링할 유저들의 정보
    }

}
