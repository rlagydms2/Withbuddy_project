package com.example.withbuddy_project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponse {
    private Long receiverId;
    private String username;
    private Long senderId;
    private boolean accept;
}
