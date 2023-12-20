package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.domain.dto.ChatDto;
import com.example.withbuddy_project.service.ChatServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatServiceImpl chatServiceImpl;
//    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatDto sendMessage(@DestinationVariable String roomId, ChatDto message) {
        chatServiceImpl.create(roomId,message.getSenderId(),message.getMessage());

        return chatServiceImpl.findChat(roomId);
    }
}
