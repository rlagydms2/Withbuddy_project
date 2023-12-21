package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.domain.dto.BuddyDto;
import com.example.withbuddy_project.domain.dto.UserDto;
import com.example.withbuddy_project.service.UserService;
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

    private final UserService userService;

//    @PostMapping("/api/home")
//    public AddressUserDto buttonList(@RequestParam(name = "addressName") String addressName) {
//        AddressUserDto addressUserDto = userService.selectAddress(addressName);
//        return addressUserDto;
//    }
@GetMapping("/api/user")
public List<UserDto> userList(Long id) {
    List<UserDto> list = userService.findWithoutMe(id); // 나빼고 다 찾기 추후 같은 지역에 사는 사람들만 나오게할 것
    return list;
}

    @GetMapping("/api/userProfile/{userId}")
    public BuddyDto userProfile(@PathVariable Long userId,Long code) {
        return userService.findBuddy(userId,code); // list에 나온 user의 정보를 찾아온걸 html에 전달
    }
//    @GetMapping("/api/user")
//    public List<User> userList(Long id,Long code) {
//        List<User> list = userService.findWithoutMe(id,code);
//        return list;
//    }

    @PostMapping("/api/dmList")
    public List<User> dmListUser(@RequestParam(name = "loginId") Long loginId) {
        return userService.findDm(loginId);     // dmList에 렌더링할 유저들의 정보
    }

}
