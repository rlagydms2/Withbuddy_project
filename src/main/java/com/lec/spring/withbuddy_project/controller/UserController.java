package com.lec.spring.withbuddy_project.controller;

import com.lec.spring.withbuddy_project.config.PrincipalDetails;
import com.lec.spring.withbuddy_project.domain.MypagePet;
import com.lec.spring.withbuddy_project.domain.User;
import com.lec.spring.withbuddy_project.domain.UserValidator;
import com.lec.spring.withbuddy_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public void login(Model model) {
    }

    @PostMapping("/loginError")
    public String loginError() {
        return "user/login";
    }

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


    // 애완견 등록
    @GetMapping("/buddy")
    public String buddy() {
        return "user/buddy";
    }


//    //  메인페이지(내정보)
//    @GetMapping("/home")  // 정보조회용도 ==> Getmapping
//    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
////        // 현재 로그인한 사용자의 아이디 가져오기
//        String userId = principalDetails.getUsername();
//
////        로그인id, err확인
////        log.info("userId : {}",userId);
////        log.error("userId : {}",userId);
//
//        User user = userService.findByUsername(userId);
//        MypagePet buddy = mypageService.getByPetBuddyId(user.getId());
////        System.out.println("user : "  + user.getUserId());
//        model.addAttribute("user",user);
//        model.addAttribute("buddy", buddy);
//        return "user/home";
//    }

    @PostMapping("/buddy")
    public String join(@AuthenticationPrincipal PrincipalDetails principalDetails,MypagePet mypagePet) {
        //        // 현재 로그인한 사용자의 아이디 가져오기

        mypagePet.setId(principalDetails.getId());
        userService.buddyregister(mypagePet);
        return "redirect:/home";
    }

    @Autowired
    UserValidator userValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }


}
