package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.Authority;
import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.domain.dto.AddressUserDto;
import com.example.withbuddy_project.domain.dto.BuddyDto;
import com.example.withbuddy_project.domain.dto.UserDto;
import com.example.withbuddy_project.repository.AuthorityRepository;
import com.example.withbuddy_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    public UserServiceImpl(SqlSession sqlSession) {
        userRepository = sqlSession.getMapper(UserRepository.class);
        authorityRepository = sqlSession.getMapper(AuthorityRepository.class);
    }


    public User findByUsername(String userId) {
        return userRepository.findByUsername(userId);
    }

    @Override
    public List<UserDto> findWithoutMe(Long id) { // 나 뺴고 찾음
        return userRepository.findAllWithoutId(id);
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public BuddyDto findBuddy(Long id,Long code) {
        return userRepository.findUserById(id,code);
    }

    @Override
    public List<User> findDm(Long loginId) { // 로그인한 유저가 갖고있는 모든 dm채팅방 정보

        List<User> list = userRepository.findDmListByLoginUserId(loginId);
        log.info("dmList user: {} ", list);
        return list;
    }

    @Override
    public List<Authority> selectAuthoritiesById(Long id) {
        User user = userRepository.findById(id);
        return authorityRepository.findByUser(user);
    }

}
