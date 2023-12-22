package com.lec.spring.withbuddy_project.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageUser {   // 마이페이지 (사용자정보)

    private Long id;
    private String userId;

    @ToString.Exclude
    private String password;

    @ToString.Exclude
    private String repassword;

    private String phone;
    private String email;
    private Long addressId;
    private String addressName;
    private String buddyImage;



}