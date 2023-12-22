package com.lec.spring.withbuddy_project.controller;


import com.lec.spring.withbuddy_project.domain.dto.ChatDto;
import com.lec.spring.withbuddy_project.service.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatApiController {
    private final ChatServiceImpl chatServiceImpl;

    @PostMapping("/api/chatList") //@RequestParam 파라미터를 요청함
    public List<ChatDto> loadChat(@RequestParam(name = "userId") Long userId, @RequestParam(name = "loginId") Long loginId) {
        List<ChatDto> list = chatServiceImpl.findAllChat(userId, loginId); // html에서 요청한 파라미터를 넣어서 서비스를 실행
//        log.info("list : {} ", list);
        return list;   // 채팅방의 모든 채팅을 담은 list
    }
}
