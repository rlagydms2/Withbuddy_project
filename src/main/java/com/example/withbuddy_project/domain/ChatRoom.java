package com.example.withbuddy_project.domain;

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
        room.setRoomId(UUID.randomUUID().toString());
        return room;
    }

}
