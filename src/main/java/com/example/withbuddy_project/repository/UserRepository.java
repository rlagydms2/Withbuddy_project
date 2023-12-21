package com.example.withbuddy_project.repository;

import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.domain.dto.AddressUserDto;
import com.example.withbuddy_project.domain.dto.BuddyDto;
import com.example.withbuddy_project.domain.dto.UserDto;

import java.util.List;

public interface UserRepository {
    int save(User user);

    User findByUsername(String userId); // userId= username으로 유저의 정보를 찾음

    User findById(Long id);

    List<User> findAllUser();  // 모든 유저를 찾음

    List<UserDto> findAllWithoutId(Long id); // 나빼고 모든 유저를 찾음

    List<User> findDmListByLoginUserId(Long loginId); // 로그인한 사람의 dmList를 찾음

    BuddyDto findUserById(Long id,Long code);  // id를 통해 유저의 정보를 찾음

    User findByEmail(String email);
}
