package com.example.withbuddy_project.repository;


import com.example.withbuddy_project.domain.Chat;
import com.example.withbuddy_project.domain.dto.ChatDto;

import java.util.List;

public interface ChatRepository {
    int save(Chat chat);

    ChatDto findChatByRoomId(String roomId);

    Chat findBySenderId(Long senderId,String roomId);

    List<ChatDto> findAllChatByRoomId(String roomId);

}
