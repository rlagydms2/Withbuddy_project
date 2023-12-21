package com.example.withbuddy_project.config;

import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// UserDetailsService
// 컨테이너에 등록한다.
// 시큐리티 설정에서 loginProcessingUrl(url) 을 설정해 놓았기에
// 로그인시 위 url 로 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는
// loadUserByUsername() 가 실행되고
// 인증성공하면 결과를 UserDetails 로 리턴
// 리턴되면 -----> Session으로 들어감
//  name : value ===> Session : Authentication(로그인 정보 객체),( principal : userdetails(로그인한 user 정보) )
@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername(" + username + ") 호출");

        // DB 조회
      User user = userService.findByUsername(username);

        // if문 : 해당 username 의 User 가 DB 에 있었다면
        // UserDetails 를 생성하여 리턴
        if(user != null){
            PrincipalDetails userDetails = new PrincipalDetails(user);
            userDetails.setUserService(userService); //userDetails에 서비스 정보도 담아서 보내기 - DB랑 연동하기 위해서
            return userDetails;
        }

        // 해당 username 이 User 가 없다면?
        // UsernameNotFoundException 을 throw 해주어야 한다
        throw new UsernameNotFoundException(username);

        // ※ 주의! 여기서 null 리턴하면 예외 발생
    }
}










