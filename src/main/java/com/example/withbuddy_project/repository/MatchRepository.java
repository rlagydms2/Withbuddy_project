package com.example.withbuddy_project.repository;


import com.example.withbuddy_project.domain.Match;
import com.example.withbuddy_project.domain.dto.MatchRequest;
import com.example.withbuddy_project.domain.dto.MatchResponse;

import java.util.List;

public interface MatchRepository {

    int save(Match match);

    Match findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    int update(MatchRequest match);

    int delete(Long matchId);

    List<MatchResponse> findByLoginUserId(Long userId);


}
