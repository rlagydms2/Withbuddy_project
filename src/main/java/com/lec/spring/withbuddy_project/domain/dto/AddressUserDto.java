package com.lec.spring.withbuddy_project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressUserDto {
    private Long addressId;
    private String addressName;
    private Long count;
}

