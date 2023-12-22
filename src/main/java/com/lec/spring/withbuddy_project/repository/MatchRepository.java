package com.lec.spring.withbuddy_project.repository;


import com.lec.spring.withbuddy_project.domain.Match;
import com.lec.spring.withbuddy_project.domain.dto.MatchRequest;
import com.lec.spring.withbuddy_project.domain.dto.MatchResponse;

import java.util.List;

public interface MatchRepository {

    int save(Match match); // 매칭정보 저장 accept false인 상태

    Match findBySenderIdAndReceiverId(Long senderId, Long receiverId); // 보낸이와 받는이로 match정보를 찾음

    int update(MatchRequest match); // 매칭 수락을 누르면 accept를 true로 저장

    int delete(Long matchId); // 삭제

    List<MatchResponse> findByLoginUserId(Long id); // 로그인한 사람의 accept가 false인 모든 매칭요청을 찾음


}
