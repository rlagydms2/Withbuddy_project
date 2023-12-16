package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.dto.AddressUserDto;
import com.example.withbuddy_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(SqlSession sqlSession) {
        userRepository = sqlSession.getMapper(UserRepository.class);
    }

    public AddressUserDto selectAddress(String addressName) {
        return userRepository.findByAddressId(addressName);
    }
}
