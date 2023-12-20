package com.example.withbuddy_project.repository;

import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.domain.dto.AddressUserDto;

import java.util.List;

public interface UserRepository {
    AddressUserDto findByAddressId(String addressName);

    User findByUsername(String userId); // userId= username으로 유저의 정보를 찾음

    User findById(Long id); // id를 통해 유저의 정보를 찾음

    List<User> findAllUser();  // 모든 유저를 찾음

    List<User> findAllWithoutId(Long id); // 나빼고 모든 유저를 찾음

    List<User> findDmListByLoginUserId(Long loginId); // 로그인한 사람의 dmList를 찾음

}
