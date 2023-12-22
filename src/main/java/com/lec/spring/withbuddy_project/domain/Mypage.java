package com.lec.spring.withbuddy_project.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mypage {   // 마이페이지 (사용자정보 + 펫정보)

    private Long id;
    private String userId;

    @ToString.Exclude
    private String password;

    private String phone;
    private String email;
    private String addressName;
    private String buddyName;
    private String category;
    private String buddyAge;
    private Integer buddySex;
    private String buddyDetail;
    private String buddyImage;


}