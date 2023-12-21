package com.example.withbuddy_project.domain;

import com.example.withbuddy_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {


    @Autowired
    UserService userService;  // 회원가입시 입력한 username 이 이미 가입한 username 인지 확인하려면 DB 접근 필요

    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println("supports(" + clazz.getName() + ")");
        // ↓ 검증할 객체의 클래스 타입인지 확인
        boolean result = User.class.isAssignableFrom(clazz);
        System.out.println(result);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        String userId = user.getUserId();
        if(userId == null || userId.trim().isEmpty()) {
            errors.rejectValue("userId", "userId 은 필수입니다"); // rejectValue(field, errorcode)
        } else if(userService.isExist(userId)){
            // 이미 등록된 중복된 아이디(username) 이 들어오면
            errors.rejectValue("userId", "이미 존재하는 아이디 입니다");
        }

        String regExpId = "^[a-zA-Z]{1}[0-9a-zA-Z]{4,20}$";
        if (!Pattern.matches(regExpId, user.getUserId().trim())) {
            errors.rejectValue("userId", "아이디는 6~20자 영문과 숫자로 이루어져 있어야합니다");
        }
        String regExpPw = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}";
        if (!Pattern.matches(regExpPw, user.getPassword().trim())) {
            errors.rejectValue("password", "비밀번호는  8~20자 사이의 영어+숫자+특수문자로 이루어져 있어야 합니다.");
        }
        String regExpEmail = "^[a-zA-Z0-9+\\-_.]+@[a-zA-Z0-9\\-]+\\.[a-zA-Z0-9\\-._]+$";
        if (!Pattern.matches(regExpEmail, user.getEmail().trim())) {
            errors.rejectValue("email", "이메일 형식에 맞지 않습니다.");
        }

//        String regExpName="^[가-힣]+$";
//        if (!Pattern.matches(regExpName, user.getName().trim())) {
//            errors.rejectValue("name","이름 형식에 맞지 않습니다.");
//        }

        String regExpPhone = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
        if (!Pattern.matches(regExpPhone, user.getPhone().trim())) {
            errors.rejectValue("phone", "전화번호 형식에 맞지 않습니다.");
        }
//        String regExpJumin = "^[\\d]{6}-[1-4][\\d]{6}+$";
//        if (!Pattern.matches(regExpJumin, user.getJuminNum().trim())) {
//            errors.rejectValue("jumin", "주민등록번호 형식에 맞지 않습니다.");
//        }

        if (!user.getPassword().equals(user.getRe_password()))
            errors.rejectValue("re_password", "동일한 비밀번호를 입력해주세요.");
    }
}

