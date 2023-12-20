package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.ChatRoom;

public interface ChatRoomService {
    ChatRoom createRoom(Long userId, Long senderId);

    ChatRoom findRoomByUserId(Long userId,Long loginId);
}
