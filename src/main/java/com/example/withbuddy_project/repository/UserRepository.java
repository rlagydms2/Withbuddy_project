package com.example.withbuddy_project.repository;

import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.domain.dto.AddressUserDto;

import java.util.List;

public interface UserRepository {
    AddressUserDto findByAddressId(String addressName);

    User findByUsername(String userId);

    User findById(Long id);

    List<User> findAllUser();

    List<User> findAllWithoutId(Long id);

    List<User> findDmListByLoginUserId(Long loginId);

}
