package com.lec.spring.withbuddy_project.controller;

import com.lec.spring.withbuddy_project.config.PrincipalDetails;
import com.lec.spring.withbuddy_project.domain.*;
import com.lec.spring.withbuddy_project.service.MapService;
import com.lec.spring.withbuddy_project.service.MypageService;
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
@Slf4j
public class MypageController {

    @Autowired
    private final MypageService mypageService;
    @Autowired
    private UserService userService;

    @Autowired
    private final MapService mapService;



    // 기본생성자
    public MypageController(MypageService mypageService, MapService mapService) {
        this.mypageService = mypageService;
        this.mapService = mapService;
        System.out.println("MypageController() 생성");
    }


    //  메인페이지(내정보)
    @GetMapping("/home")  // 정보조회용도 ==> Getmapping
    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
//        // 현재 로그인한 사용자의 아이디 가져오기

        String userId = principalDetails.getUsername();

//        로그인id, err확인
//        log.info("userId : {}",userId);
//        log.error("userId : {}",userId);

        User user = userService.findByUsername(userId);

        MypagePet buddy = mypageService.getByPetBuddyId(user.getId());
//        System.out.println("user : "  + user.getUserId());
        model.addAttribute("user",user);
        model.addAttribute("buddy", buddy);
        model.addAttribute("location",mapService.locationData());
        return "/home";
    }


    //  마이페이지(사용자정보 수정)
    @GetMapping("/user/mypageuser")
    public String mypageuser(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String userId = principalDetails.getUsername();
        User user = userService.findByUsername(userId);
        model.addAttribute("user", user);
        return "/user/mypageuser";
    }


    //  마이페이지(사용자정보 수정완료) -- 작성 미완
    @PostMapping("/user/mypageuser")   // 저장==>PostMapping , Getmapping으로 html확인
    public String mypageuserOk(
            @Valid User user    // 업데이트할 정보(Valid로 유효성검사)
            , BindingResult bindingResult   // 검증결과를 받는 매개변수(유효성검사결과확인)
            , Model model   //  담아서 보내는 용도
            , RedirectAttributes redirectAttributes) {  // redirect시 데이터를 전달하기위해(에러날경우 에러메시지와 입력값을 다시 전달하기위해 사용)
        System.out.println("UUUUU" + user);
        // Validator적용
//        mypageValidator.validate(mypageService, bindingResult);

        if (bindingResult.hasErrors()) { // 에러가 날 경우

            redirectAttributes.addFlashAttribute("password", user.getPassword());
            redirectAttributes.addFlashAttribute("phone", user.getPhone());
            redirectAttributes.addFlashAttribute("email", user.getEmail());
            redirectAttributes.addFlashAttribute("address", user.getAddressId());


            for (var err : bindingResult.getFieldErrors()) {

                redirectAttributes.addFlashAttribute("error_" + err.getField() + err.getCode());

            }
            return "redirect:/user/mypageuser";
        }


        model.addAttribute("result", mypageService.updateUser(user));
        System.out.println("user : " + user);

        return "/user/mypage";
    }


    // 마이페이지 (펫정보수정)
    @GetMapping("/user/mypagepet")
    public String mypagepet(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        String userId = principalDetails.getUsername();
        User user = userService.findByUsername(userId);
        MypagePet buddy = mypageService.getByPetBuddyId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("buddy", buddy);
        return "/user/mypagepet";
    }


    // 마이페이지 (펫정보수정완료) -- 작성 미완
    @PostMapping("/user/mypagepet")
    public String mypagepetOk(
            @RequestParam Map<String, MultipartFile> files
            , @Valid MypagePet mypagePet
            , BindingResult bindingResult
            , Model model
            , RedirectAttributes redirectAttributes) {

//        if (bindingResult.hasErrors()) { // 에러가 날 경우
//
//            redirectAttributes.addFlashAttribute("buddyName", mypagePet.getBuddyName());
//            redirectAttributes.addFlashAttribute("category", mypagePet.getCategory());
//            redirectAttributes.addFlashAttribute("buddyAge", mypagePet.getBuddyAge());
//            redirectAttributes.addFlashAttribute("buddySex", mypagePet.getBuddySex());
//            redirectAttributes.addFlashAttribute("buddyDetail", mypagePet.getBuddyDetail());
//            redirectAttributes.addFlashAttribute("buddyImage", mypagePet.getBuddyImage());
//
//            for (var err : bindingResult.getFieldErrors()) {
//
//                redirectAttributes.addFlashAttribute("error_" + err.getField() + err.getCode());
//
//            }
//            return "redirect:/user/mypagepet";
//        }

        model.addAttribute("result", mypageService.updatePet(mypagePet, files));
        return "/user/mypage";
    }


    //  마이페이지(사용자정보 + 펫정보)
    @GetMapping("/user/mypage")  // 정보조회용도
    public String mypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        String userId = principalDetails.getUsername();
        User user = userService.findByUsername(userId);
        String addressName = mypageService.checkaddress(user.getId());
        MypagePet buddy = mypageService.getByPetBuddyId(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("addressName",addressName);
        model.addAttribute("buddyName", buddy.getBuddyName());
        model.addAttribute("category", buddy.getCategory());
        model.addAttribute("buddyAge", buddy.getBuddyAge());
        model.addAttribute("buddySex", buddy.getBuddySex());
        model.addAttribute("buddyDetail", buddy.getBuddyDetail());
        model.addAttribute("buddyImage", buddy.getBuddyImage());
        return "/user/mypage";
    }

    @Autowired
    private MypageValidator mypageValidator;

    @InitBinder("MypageUser")
    public void initiBinder(WebDataBinder binder) {
        binder.setValidator(mypageValidator);
    }

}
