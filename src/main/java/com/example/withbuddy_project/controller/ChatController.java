package com.example.withbuddy_project.controller;

import com.example.withbuddy_project.domain.dto.ChatDto;
import com.example.withbuddy_project.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
//    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatDto sendMessage(@DestinationVariable String roomId, ChatDto message) {
        chatService.create(roomId,message.getSenderId(),message.getMessage());

        return chatService.findChat(roomId);
    }
}
