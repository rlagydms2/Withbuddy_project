package com.lec.spring.withbuddy_project.service;

import com.lec.spring.withbuddy_project.domain.Chat;
import com.lec.spring.withbuddy_project.domain.ChatRoom;
import com.lec.spring.withbuddy_project.domain.dto.ChatDto;
import com.lec.spring.withbuddy_project.repository.ChatRepository;
import com.lec.spring.withbuddy_project.repository.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@Slf4j
public class ChatServiceImpl implements ChatService{

    private ChatRepository chatRepository;

    private ChatRoomRepository chatRoomRepository;

    public ChatServiceImpl(SqlSession sqlSession) {
        chatRepository = sqlSession.getMapper(ChatRepository.class);
        chatRoomRepository = sqlSession.getMapper(ChatRoomRepository.class);
    }


    @Override
    public void create(String roomId,Long senderId, String message) { //send받은 정보를 토대로 채팅메시지 만들어 저장
        ChatRoom chatRoom = chatRoomRepository.findById(roomId);
        int save = chatRepository.save(Chat.createChat(chatRoom, senderId, message));
        log.info("save: {} ", save);

    }

    @Override
    public ChatDto findChat(String roomId) { // 채팅방의 가장 최신 채팅을 찾음
        ChatDto chat = chatRepository.findChatByRoomId(roomId);
        return chat;
    }

    @Override
    public List<ChatDto> findAllChat(Long userId,Long loginId) { // 로그인한 사람과 매칭된 사람이 쓰는 채팅방의 모든 채팅을 찾음
        ChatRoom roomId = chatRoomRepository.findByUserId(userId, loginId);
        List<ChatDto> list = chatRepository.findAllChatByRoomId(roomId.getRoomId());
        return list;
    }

}
