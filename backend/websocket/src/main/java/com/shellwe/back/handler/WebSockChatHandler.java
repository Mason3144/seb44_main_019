package com.shellwe.back.handler;

import com.shellwe.back.service.WsChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class WebSockChatHandler extends TextWebSocketHandler {
    private final WsChatService wsChatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception, IOException{
        wsChatService.getPreviousMessages(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        wsChatService.handleMessage(session,message);
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        wsChatService.terminateSession(session);
    }
}















