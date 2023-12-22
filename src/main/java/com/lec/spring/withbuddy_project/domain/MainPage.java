package com.lec.spring.withbuddy_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainPage {  // 메인페이지
    private Long id;
    private String buddyImage;    // 펫사진
    private String userId;    // 사용자Id
    private String buddyName;   // 펫이름
    private String category;    // 견종


}