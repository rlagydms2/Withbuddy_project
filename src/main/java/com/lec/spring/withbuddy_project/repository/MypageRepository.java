package com.lec.spring.withbuddy_project.repository;

import com.lec.spring.withbuddy_project.domain.*;
import net.minidev.json.JSONUtil;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Repository
public interface MypageRepository {


    //  메인페이지 사용자 정보
    List<MainPage> getByuserIdMain(String userId);


    // 마이페이지 사용자정보
    List<MypageUser> getByMyInfoUserId(String userId);


    // 마이페이지 펫정보
    MypagePet getByPetUserId(Long userId);


    // 마이페이지 (사용자정보 + 펫정보)
    List<Mypage> getByMypageUserId(Long userId);


    // 사용자정보수정
    int updateUser (User user);



    int updatePet(Map<String, MultipartFile> files, MypagePet mypagePet);

    String checkaddress(Long userId);
}