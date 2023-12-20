package com.example.withbuddy_project.controller;


import com.example.withbuddy_project.domain.dto.MatchRequest;
import com.example.withbuddy_project.domain.dto.MatchResponse;
import com.example.withbuddy_project.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchApiController {
    private final MatchService matchService;
//    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/api/match")
    public int matchSave(@RequestParam(name = "senderId") Long senderId, @RequestParam(name = "receiverId") Long receiverId) {
        log.info("receiverId={}", receiverId);
        int save = matchService.save(senderId, receiverId);
        boolean match = matchService.ifExist(senderId, receiverId);
        return (match != true) ? save : 0;
    }

    @GetMapping("/api/alert/{userId}")
    public List<MatchResponse> matchList(@PathVariable(name = "userId") Long userId) {
        List<MatchResponse> list = matchService.list(userId);
        log.info("matchList : {} " ,list);
        return list;
    }

    @PutMapping("/api/matchUpdate")
    public void matchAccept(@RequestParam(name = "senderId") Long senderId, @RequestParam(name = "receiverId") Long receiverId) {
        MatchRequest match = MatchRequest.builder()
                .senderId(receiverId)
                .receiverId(senderId)
                .build();
        log.info("matchAccept : {} ", match);
        MatchRequest match2 = MatchRequest.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        matchService.acceptMatch(match);
        matchService.acceptMatch(match2);
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
        matchService.delete(match1);
        matchService.delete(match2);
    }
}
