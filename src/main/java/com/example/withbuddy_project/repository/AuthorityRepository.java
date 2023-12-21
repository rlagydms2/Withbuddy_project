package com.example.withbuddy_project.repository;

import com.example.withbuddy_project.domain.Authority;
import com.example.withbuddy_project.domain.User;

import java.util.List;

public interface AuthorityRepository {
    // 특정 이름(name) 의 권한 정보 읽어오기
    Authority findByName(String name);


    // 특정 사용자(User) 의 권한(들) 읽어오기
    List<Authority> findByUser(User user);

    //  권한부여(INSERT)
//    int addAuthority(int auth_id,String AuthorityName);

}



