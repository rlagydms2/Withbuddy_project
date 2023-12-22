package com.lec.spring.withbuddy_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypagePet {    // 마이페이지(펫정보)
    private Long buddyId;
    private String buddyName;
    private String category;
    private String buddyAge;
    private int buddySex;
    private String buddyDetail;
    private String buddyImage;
    private Long id;

}
