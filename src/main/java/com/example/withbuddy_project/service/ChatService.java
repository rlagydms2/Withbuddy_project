package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.dto.ChatDto;

import java.util.List;

public interface ChatService {
    void create(String roomId,Long senderId, String message);

    ChatDto findChat(String roomId);

    List<ChatDto> findAllChat(Long userId, Long loginId);
}
