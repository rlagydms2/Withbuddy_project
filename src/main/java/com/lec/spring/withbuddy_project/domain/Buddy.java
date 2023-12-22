package com.lec.spring.withbuddy_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Buddy {
    private Long buddyId;
    private String category;
    private String buddyName;
    private Long buddyAge;
    private String buddyImage;
    private String buddyDetail;
    private Long buddySex;
    private Long id;
    private User user;
}
