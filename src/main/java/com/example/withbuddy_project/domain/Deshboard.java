package com.example.withbuddy_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deshboard {
    private Long id;
    private String userId;
    private String email;
    private Long matchCnt;
    private String address;
    private String name;
    private String category;
    private Long age;
    private String sex;
}
