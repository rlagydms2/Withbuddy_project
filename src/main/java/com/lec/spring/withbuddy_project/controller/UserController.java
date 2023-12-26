package com.lec.spring.withbuddy_project.controller;


import com.lec.spring.withbuddy_project.config.PrincipalDetails;
import com.lec.spring.withbuddy_project.domain.MypagePet;
import com.lec.spring.withbuddy_project.domain.User;
import com.lec.spring.withbuddy_project.domain.UserValidator;
import com.lec.spring.withbuddy_project.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public void login() {
    }

    //  .loginProcessingUrl("/user/login") 이 설정된 경우
    //  Security 가 낚아채기 때문에 아래 핸들러는 실행되지 않는다
    // login post  확인용!!!!
//    @PostMapping("/login")
//    public void loginProcess(){
//        System.out.println("POST: /user/login 요청 발생!");
//
//    }

    // onAuthenticationFailure 에서 로그인 실패시 forwarding 용
    // request 에 담겨진 attribute 는 Thymeleaf 에서 그대로 표현 가능.
    @PostMapping("/loginError")
    public String loginError() {
        return "user/login";
    }

    @RequestMapping("/rejectAuth")
    public String rejectAuth() {
        return "user/rejectAuth";
    }

//    @PostMapping("/register")
//    public String join(User user, Model model){
//        int cnt = userService.register(user);
//        model.addAttribute("result", cnt);
//        return "/user/registerOk";
//    }

    @PostMapping("/register")
    public String registerOk(@Valid User user
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttrs
    ) {
        System.out.println("POST: /user/register 요청발생");
        // 검증 에러가 있었다면 redirect 한다
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("userId", user.getUserId());
            redirectAttrs.addFlashAttribute("password", user.getPassword());
            redirectAttrs.addFlashAttribute("email", user.getEmail());
            redirectAttrs.addFlashAttribute("phone", user.getPhone());

            for (var err : result.getFieldErrors()) {
                redirectAttrs.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

//            List<FieldError> errList = result.getFieldErrors();
//            for(FieldError err : errList) {
//                redirectAttrs.addFlashAttribute("error", err.getCode());  // 가장 처음에 발견된 에러를 담아ㅣ 보낸다
//                break;
//            }

            return "redirect:/user/login";
        }

        // 에러 없었으면 회원 등록 진행
        int cnt = userService.register(user);
        model.addAttribute("result", cnt);


        return "/user/registerOk";
    }

    //일반 로그인 완료후 메인페이지로
    @GetMapping("/main")
    public void main() {
    }


    // 애완견 등록
    @GetMapping("/buddy")
    public String buddy() {
        return "user/buddy";
    }


    @PostMapping("/buddy") // 12/26 수정
    public String join(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            MypagePet mypagePet,
            User user,
            @RequestParam Map<String, MultipartFile> buddyFile
    ) {
        // 현재 로그인한 사용자의 아이디 가져오기
        mypagePet.setId(principalDetails.getId());
        // 주소설정 필요
        user.setId(principalDetails.getId());
        user.setUserId(principalDetails.getUsername());
        user.setPassword(principalDetails.getPassword());
        user.setEmail(principalDetails.getEmail());
        user.setPhone(principalDetails.getPhone());

        System.out.println(buddyFile);
        userService.buddyregister(mypagePet,buddyFile);
        System.out.println(user);
        userService.address(user);
        return "redirect:/home";
    }

    //아이디 찾기
    @GetMapping("/findID")
    public void findID() {
    }
    //비밀번호 찾기
    @GetMapping("/findPW")
    public void findPW() {
    }
//    //로그아웃
//    @PostMapping("/logout")
//    public String logout(HttpSession session){
//
//        String sessionName = "userId";
//
//        // 세션 삭제
//        session.removeAttribute(sessionName);
//
//        return "session/logout";
//    }


    @Autowired
    UserValidator userValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }


}
