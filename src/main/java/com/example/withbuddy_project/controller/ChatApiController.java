package com.example.withbuddy_project.controller;


import com.example.withbuddy_project.domain.dto.ChatDto;
import com.example.withbuddy_project.service.ChatServiceImpl;
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

    @PostMapping("/api/chatList")
    public List<ChatDto> loadChat(@RequestParam(name = "userId") Long userId, @RequestParam(name = "loginId") Long loginId) {
        List<ChatDto> list = chatServiceImpl.findAllChat(userId, loginId);
        log.info("list : {} ", list);
        return list;
    }
}
