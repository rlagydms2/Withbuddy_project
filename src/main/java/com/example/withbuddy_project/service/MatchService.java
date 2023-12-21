package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.dto.MatchRequest;
import com.example.withbuddy_project.domain.dto.MatchResponse;

import java.util.List;

public interface MatchService {
    int save(Long senderId,Long receiverId);

    boolean ifExist(Long senderId,Long receiverId);

    int acceptMatch(MatchRequest match);

    List<MatchResponse> list(Long userId);
    void delete(MatchRequest request);
}
