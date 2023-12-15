package com.example.withbuddy_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private int addressid;
    private int authorityuid;
    private String userid;
    private String password;
    private String phone;
    private String email;
    private int reportcount;
}
