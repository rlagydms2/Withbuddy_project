package com.lec.spring.withbuddy_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatRoom {
    private String  roomId;

    public static ChatRoom create() {
        ChatRoom room=new ChatRoom();
        room.setRoomId(UUID.randomUUID().toString()); //채팅방 id를 랜덤으로 만들어서 저장하기 위함
        return room;
    }

}
