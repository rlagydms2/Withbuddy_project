package com.lec.spring.withbuddy_project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponse {
    private String buddyImage;
    private Long receiverId;
    private String userId;
    private Long senderId;
    private boolean accept;
}
