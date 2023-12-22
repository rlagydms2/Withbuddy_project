package com.lec.spring.withbuddy_project.service;

import com.lec.spring.withbuddy_project.domain.MainPage;
import com.lec.spring.withbuddy_project.domain.Mypage;
import com.lec.spring.withbuddy_project.domain.MypagePet;
import com.lec.spring.withbuddy_project.domain.MypageUser;
import com.lec.spring.withbuddy_project.repository.MypageRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class MypageServiceImpl implements MypageService {

    private final MypageRepository mypageRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public MypageServiceImpl(SqlSession sqlSession) {
        mypageRepository = sqlSession.getMapper(MypageRepository.class);
        System.out.println("MypageServiceImpl() 생성");
    }
//    @Autowired
//    public MypageServiceImpl(MypageRepository mypageRepository) {
//        this.mypageRepository = mypageRepository;
//        System.out.println("MypageServiceImpl() 생성");
//    }


    // 메인페이지 사용자정보 (조회용)
    @Override
    public List<MainPage> getByuserIdMain(String userId) {
        List<MainPage> main = mypageRepository.getByuserIdMain(userId);
        return main;
    }


    // 마이페이지 사용자정보 (조회용)
    @Override
    public List<MypageUser> getByMyInfoUserId(String userId) {
        List<MypageUser> mypageUsers = mypageRepository.getByMyInfoUserId(userId);
        return mypageUsers;
    }


    // 마이페이지 펫정보 (조회용)
    @Override
    public MypagePet getByPetBuddyId(Long id) {
        MypagePet mypagePets = mypageRepository.getByPetUserId(id);
        return mypagePets;
    }


    // 마이페이지 (조회용)
    @Override
    public List<Mypage> getByMypageUserId(Long userId) {
        List<Mypage> mypages = mypageRepository.getByMypageUserId(userId);
        return mypages;
    }


    // 사용자정보 수정
    @Override
    public int updateUser(MypageUser mypageuser) {

        // 비밀번호 인코딩
        mypageuser.setPassword(passwordEncoder.encode(mypageuser.getPassword()));

        return mypageRepository.updateUser(mypageuser);
    }


    // 펫정보 수정
    @Override
    public int updatePet(MypagePet mypagePet, Map<String, MultipartFile> files) {
        return mypageRepository.updatePet(files, mypagePet);
    }


}