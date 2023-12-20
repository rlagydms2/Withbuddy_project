package com.example.withbuddy_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat {
    private Long id;
    private String roomId;
    private Long senderId;
    private String message;
    private LocalDateTime sendTime;

    @Builder
    public Chat(ChatRoom chatRoom,Long senderId, String message) {
        this.roomId=chatRoom.getRoomId();
        this.senderId=senderId;
        this.message=message;
        this.sendTime=LocalDateTime.now();
    }

    public static Chat createChat(ChatRoom chatRoom, Long senderId, String message) {
        return Chat.builder()
                .roomId(chatRoom.getRoomId())
                .senderId(senderId)
                .message(message)
                .build();
    }

}
