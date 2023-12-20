package com.example.withbuddy_project.controller;


import com.example.withbuddy_project.domain.dto.MatchRequest;
import com.example.withbuddy_project.domain.dto.MatchResponse;
import com.example.withbuddy_project.service.MatchServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchApiController {
    private final MatchServiceImpl matchServiceImpl;
//    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/api/match")
    public int matchSave(@RequestParam(name = "senderId") Long senderId, @RequestParam(name = "receiverId") Long receiverId) {
        log.info("receiverId={}", receiverId);
        int save = matchServiceImpl.save(senderId, receiverId); //매칭하기 버튼을 누르면 매칭을 만듬
        boolean match = matchServiceImpl.ifExist(senderId, receiverId); // 만약 매칭이 있다면 0을 리턴 아니면 1을 리턴해서 매칭을 만들지 말지 결정
        return (match != true) ? save : 0;
    }

    @GetMapping("/api/alert/{userId}")
    public List<MatchResponse> matchList(@PathVariable(name = "userId") Long userId) {
        List<MatchResponse> list = matchServiceImpl.list(userId); // "userId==로그인한 유저의 아이디" 에 따른 매칭 리스트를 렌더링하기 위한 정보
        log.info("matchList : {} " ,list);
        return list;
    }

    @PutMapping("/api/matchUpdate")
    public void matchAccept(@RequestParam(name = "senderId") Long senderId, @RequestParam(name = "receiverId") Long receiverId) {
        MatchRequest match = MatchRequest.builder() // 받아온 정보를 토대로 매칭 정보를 보내서
                .senderId(receiverId)
                .receiverId(senderId)
                .build();
        log.info("matchAccept : {} ", match);
        MatchRequest match2 = MatchRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        matchServiceImpl.acceptMatch(match);  // accept여부를 true로 업데이트
        matchServiceImpl.acceptMatch(match2);
    }

    @DeleteMapping("/api/matchDelete")
    public void matchDelete(@RequestParam(name = "senderId") Long senderId,@RequestParam(name = "receiverId") Long receiverId) {
        MatchRequest match1=MatchRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        MatchRequest match2 = MatchRequest.builder()
                .senderId(receiverId)
                .receiverId(senderId)
                .build();
        matchServiceImpl.delete(match1);  // 매칭 수락을 거절하면 delete함
        matchServiceImpl.delete(match2);
    }
}
