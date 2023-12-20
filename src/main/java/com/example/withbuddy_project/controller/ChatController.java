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

    @MessageMapping("/chat/{roomId}") // prefix/chat/{roomId}에서 메시지 받음
    @SendTo("/topic/chat/{roomId}")  // 외부 브로커가 /topic/chat/{roomId} 로 메시지를 전달
    public ChatDto sendMessage(@DestinationVariable String roomId, ChatDto message) { // roomId가 위의 어노테이션들에 바인딩 되어야함을 알려줌
        chatServiceImpl.create(roomId,message.getSenderId(),message.getMessage());  // 채팅을 만들기 위해 ChatDto message에 send를 통해 받아온 정보를 저장

        return chatServiceImpl.findChat(roomId); // roomId에 채팅을 전달하기 위해 roomId로 보낸 채팅을 찾기
    }
}
