package com.lec.spring.withbuddy_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority {
    private int id;  // PK
    private String authorityName;  // 권한명 ex) "ROLE_MEMBER", "ROLE_ADMIN"
}
