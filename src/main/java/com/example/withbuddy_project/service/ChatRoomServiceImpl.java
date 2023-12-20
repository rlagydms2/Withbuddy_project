package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.ChatRoom;
import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.repository.ChatRoomRepository;
import com.example.withbuddy_project.repository.UserRepository;
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
    public ChatRoom createRoom(Long userId, Long senderId) {
        ChatRoom room = ChatRoom.create();
        chatRoomRepository.save(room);
        User user = userRepository.findById(userId);
        User user2 = userRepository.findById(senderId);
        chatRoomRepository.addUserList(room.getRoomId(), user.getId());
        chatRoomRepository.addUserList(room.getRoomId(), user2.getId());

        return room;
    }

    @Override
    public ChatRoom findRoomByUserId(Long userId,Long loginId) {
        ChatRoom room = chatRoomRepository.findByUserId(userId,loginId);
        return room;
    }
}
