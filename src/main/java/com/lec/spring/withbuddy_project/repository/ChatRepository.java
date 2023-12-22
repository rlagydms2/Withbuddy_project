package com.lec.spring.withbuddy_project.repository;


import com.lec.spring.withbuddy_project.domain.Chat;
import com.lec.spring.withbuddy_project.domain.dto.ChatDto;

import java.util.List;

public interface ChatRepository {
    int save(Chat chat); // 전달받은 채팅을 저장

    ChatDto findChatByRoomId(String roomId); // roomId의 가장 최신 채팅을 찾음

    Chat findBySenderId(Long senderId,String roomId); // 보낸이의 채팅 정보를 찾음

    List<ChatDto> findAllChatByRoomId(String roomId); // 채팅방의 모든 채팅을 찾아옴

}
