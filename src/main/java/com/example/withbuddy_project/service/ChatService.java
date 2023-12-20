package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.Chat;
import com.example.withbuddy_project.domain.ChatRoom;
import com.example.withbuddy_project.domain.dto.ChatDto;
import com.example.withbuddy_project.repository.ChatRepository;
import com.example.withbuddy_project.repository.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@Slf4j
public class ChatService {

    private ChatRepository chatRepository;

    private ChatRoomRepository chatRoomRepository;

    public ChatService(SqlSession sqlSession) {
        chatRepository = sqlSession.getMapper(ChatRepository.class);
        chatRoomRepository = sqlSession.getMapper(ChatRoomRepository.class);
    }


    public void create(String roomId,Long senderId, String message) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId);
        int save = chatRepository.save(Chat.createChat(chatRoom, senderId, message));
        log.info("save: {} ", save);

    }

    public ChatDto findChat(String roomId) {
        ChatDto chat = chatRepository.findChatByRoomId(roomId);
        return chat;
    }

    public List<ChatDto> findAllChat(Long userId,Long loginId) {
        ChatRoom roomId = chatRoomRepository.findByUserId(userId, loginId);
        List<ChatDto> list = chatRepository.findAllChatByRoomId(roomId.getRoomId());
        return list;
    }

}
