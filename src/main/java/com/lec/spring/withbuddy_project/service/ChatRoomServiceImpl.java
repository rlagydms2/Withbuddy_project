package com.lec.spring.withbuddy_project.service;

import com.lec.spring.withbuddy_project.domain.ChatRoom;
import com.lec.spring.withbuddy_project.domain.User;
import com.lec.spring.withbuddy_project.repository.ChatRoomRepository;
import com.lec.spring.withbuddy_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{


    private final ChatRoomRepository chatRoomRepository;


    private final UserRepository userRepository;



    public ChatRoomServiceImpl(SqlSession sqlSession) {
        this.chatRoomRepository = sqlSession.getMapper(ChatRoomRepository.class);
        this.userRepository = sqlSession.getMapper(UserRepository.class);
    }

    @Override
    public ChatRoom createRoom(Long userId, Long senderId) {  // 채팅방 만들고 저장
        ChatRoom room = ChatRoom.create();
        chatRoomRepository.save(room);
        User user = userRepository.findById(userId);
        User user2 = userRepository.findById(senderId);
        chatRoomRepository.addUserList(room.getRoomId(), user.getId());
        chatRoomRepository.addUserList(room.getRoomId(), user2.getId());

        return room;
    }

    @Override
    public ChatRoom findRoomByUserId(Long userId,Long loginId) { // userId와 loginId가 일치하는 채팅방 찾기
        ChatRoom room = chatRoomRepository.findByUserId(userId,loginId);
        return room;
    }
}
