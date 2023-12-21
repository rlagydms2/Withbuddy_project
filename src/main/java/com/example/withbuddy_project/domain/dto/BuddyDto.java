package com.example.withbuddy_project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuddyDto {
    private Long id;
    private String userId;
    private String category;
    private String buddyName;
    private String buddyImage;
    private Long buddyAge;
    private String buddyDetail;
    private boolean buddySex;
}
