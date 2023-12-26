package com.lec.spring.withbuddy_project.service;

import com.lec.spring.withbuddy_project.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MypageService {


    // 메인페이지 사용자정보
    List<MainPage> getByuserIdMain(String userId);


    // 마이페이지 사용자정보
    List<MypageUser> getByMyInfoUserId(String userId);


    // 마이페이지 펫정보
    MypagePet getByPetBuddyId(Long userId);


    // 마이페이지
    List<Mypage> getByMypageUserId(Long buddyId);


    // 사용자정보 수정
    int updateUser(User user);

    int updatePet(Map<String, MultipartFile> files,MypagePet mypagePet);

    String checkaddress(Long userId);
}