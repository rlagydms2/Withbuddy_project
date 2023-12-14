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
    private String userid;
    private String passwrod;
    private String phone;
    private String email;
    private String regionAddress;
    private Long reportCount;
    private String townAddress;
}
