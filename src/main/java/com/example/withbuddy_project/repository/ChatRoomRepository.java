package com.example.withbuddy_project.repository;


import com.example.withbuddy_project.domain.ChatRoom;

import java.util.List;


public interface ChatRoomRepository {
    int save(ChatRoom chatRoom);


    int addUserList(String roomId,Long userId);

    List<ChatRoom> findAllRoomIdByUserId(Long userId);

    ChatRoom findById(String roomId);

    ChatRoom findByUserId(Long userId,Long loginId);

    void deleteById(ChatRoom chatRoom);
}
