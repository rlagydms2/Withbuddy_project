package com.example.withbuddy_project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker  // 웹소켓 브로커 사용하기 위한 어노테이션
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // ex) localhost:8080/ws stomp connect를 위한 url
                .setAllowedOriginPatterns("*")  //  모든 url에 대해 허용하겠다
                .withSockJS();   // sockJS 사용
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue"); // 외부브로커에게 보내는 통로 /topic은 구독자 전체 /queue는 point to point
        registry.setApplicationDestinationPrefixes("/home"); // prefix에 메시지를 전달
    }
}
