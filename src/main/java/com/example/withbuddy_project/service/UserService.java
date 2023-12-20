package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.domain.dto.AddressUserDto;
import com.example.withbuddy_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;

    public UserService(SqlSession sqlSession) {
        userRepository = sqlSession.getMapper(UserRepository.class);
    }

    public AddressUserDto selectAddress(String addressName) {
        return userRepository.findByAddressId(addressName);
    }

    public User findByUsername(String userId) {
        return userRepository.findByUsername(userId);
    }


    public List<User> findWithoutMe(Long id) {
        return userRepository.findAllWithoutId(id);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findUsername(Long loginId) {

        List<User> list = userRepository.findDmListByLoginUserId(loginId);
        log.info("dmList user: {} ", list);
        return list;
    }
}
