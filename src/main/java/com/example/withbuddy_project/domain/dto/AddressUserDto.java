package com.example.withbuddy_project.domain.dto;

import com.example.withbuddy_project.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressUserDto {
    private Long addressId;
    private String addressName;
    private Long count;
}

