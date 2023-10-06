package com.shellwe.back.config;

import com.shellwe.back.handler.CustomHandshakeHandler;
import com.shellwe.back.handler.WebSockChatHandler;
import com.shellwe.back.handler.WebSockRoomHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSockConfig implements WebSocketConfigurer {
    private final WebSockChatHandler webSockChatHandler;
    private final WebSockRoomHandler webSockRoomHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSockChatHandler, "/ws/chat")
                .addHandler(webSockRoomHandler,"/ws/room")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .setAllowedOrigins("*");
    }
}