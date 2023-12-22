package com.lec.spring.withbuddy_project.repository;


import com.lec.spring.withbuddy_project.domain.ChatRoom;

import java.util.List;


public interface ChatRoomRepository {
    int save(ChatRoom chatRoom);  // 채팅방 id 저장

    int addUserList(String roomId,Long userId); // userList라는 관계 테이블에 roomId와 userId를 저장함
                                                // 여기서 chatroom과 user는 다대다관계
    List<ChatRoom> findAllRoomIdByUserId(Long userId); // user가 매칭을 한 유저와 만든 모든 채팅방

    ChatRoom findById(String roomId);

    ChatRoom findByUserId(Long userId,Long loginId); // 1:1 채팅을 위해 로그인한 사람의 id와 1:1매칭되는 사람의 아이디를 가진 채팅방을 찾음

    void deleteById(ChatRoom chatRoom); // 삭제
}
