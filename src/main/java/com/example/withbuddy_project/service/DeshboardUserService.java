package com.example.withbuddy_project.service;

import java.util.List;



public interface DeshboardUserService {
    // 대쉬보드 지역별 유저 수 조회
    List<String> address();

    List<Integer> usercount();
}
