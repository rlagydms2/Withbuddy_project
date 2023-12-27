package com.lec.spring.withbuddy_project.controller;

import com.lec.spring.withbuddy_project.domain.User;
import com.lec.spring.withbuddy_project.domain.dto.BuddyDto;
import com.lec.spring.withbuddy_project.domain.dto.UserDto;
import com.lec.spring.withbuddy_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

//    @PostMapping("/api/home")
//    public AddressUserDto buttonList(@RequestParam(name = "addressName") String addressName) {
//        AddressUserDto addressUserDto = userService.selectAddress(addressName);
//        return addressUserDto;
//    }
    @GetMapping("/api/user")
    public List<UserDto> userList(Long id,Long addressId) {
        List<UserDto> list = userService.findWithoutMe(id,addressId); // 나빼고 다 찾기 추후 같은 지역에 사는 사람들만 나오게할 것
        return list;
    }

    @GetMapping("/api/userProfile/{userId}")
    public BuddyDto userProfile(@PathVariable Long userId, Long code) {
        return userService.findBuddyProfile(userId,code); // list에 나온 user의 정보를 찾아온걸 html에 전달
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

    @PostMapping("api/getauthorit")
    public String authoritName(@RequestParam(name = "id") Long id){
        return userService.getAuthorityName(id);
    }

    @PostMapping("/api/AllUser")
    public List<UserDto> AllUser(@RequestParam(name = "loginId") Long loginId){
        List<UserDto> allUser = userService.findAllUser(loginId);
        return allUser;
    }

}
