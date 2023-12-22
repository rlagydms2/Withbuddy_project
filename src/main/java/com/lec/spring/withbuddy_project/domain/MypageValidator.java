package com.lec.spring.withbuddy_project.domain;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class MypageValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        boolean result = User.class.isAssignableFrom(clazz);
        System.out.println(result);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("validate() 호출");
        User user = (User) target;


        String password = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}";
        if (!Pattern.matches(password, user.getPassword().trim())) {
            errors.rejectValue("password", "비밀번호는  8~20자 사이의 영어+숫자+특수문자로 이루어져 있어야 합니다.");
        }


        if (!user.getPassword().equals(user.getRe_password())) {
            errors.rejectValue("repassword", "비밀번호가 일치하지 않습니다.");
        }

        String phone = "^\\d{2,3}-\\d{3,4}-\\d{4}$";
        if (!Pattern.matches(phone, user.getPhone().trim())) {
            errors.rejectValue("phone", "전화번호 형식에 맞지 않습니다.");
        }

        String email1 = "^[a-zA-Z0-9+\\-_.]$";
        if (!Pattern.matches(email1, user.getEmail().trim())) {
            errors.rejectValue("email", "아이디만 입력해주세요.");
        }

        String email2 = "^[a-zA-Z0-9\\-]+\\.[a-zA-Z0-9\\-._]+$";
        if (!Pattern.matches(email2, user.getEmail().trim())) {
            errors.rejectValue("email", "이메일 형식에 맞지 않습니다.");
        }

    }
}