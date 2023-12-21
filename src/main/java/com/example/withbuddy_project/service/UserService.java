package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.Authority;
import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.domain.dto.BuddyDto;
import com.example.withbuddy_project.domain.dto.UserDto;

import java.util.List;

public interface UserService {
    List<Authority> selectAuthoritiesById(Long id);

    User findByUsername(String userId);

    List<UserDto> findWithoutMe(Long id);

    User findById(Long id);

    BuddyDto findBuddy(Long id, Long code);

    List<User> findDm(Long loginId);

    default boolean isExist(String username) {
        return false;
    }

    default boolean isExistEmail(String email) {
        return false;
    }

    // 신규 회원 등록
    int register(User user);
}
