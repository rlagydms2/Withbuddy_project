package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.domain.ChatRoom;
import com.example.withbuddy_project.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatRoomApiController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/chatroom/room")
    public ChatRoom dm(@RequestParam(name = "receiverId") Long receiverId, @RequestParam(name = "senderId") Long senderId) {
        return chatRoomService.createRoom(receiverId,senderId);
    }

    @PostMapping("/chatroom/find")
    public ChatRoom findRoomId(@RequestParam(name="userId")Long userId,@RequestParam(name = "loginId") Long loginId) {
        ChatRoom room = chatRoomService.findRoomByUserId(userId, loginId);
        log.info("room: {}",room);
        return room;
    }
}
