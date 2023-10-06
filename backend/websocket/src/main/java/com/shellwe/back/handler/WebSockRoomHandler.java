package com.shellwe.back.handler;

import com.shellwe.back.service.WsRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class WebSockRoomHandler extends TextWebSocketHandler {
    private final WsRoomService wsRoomService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception, IOException{
        wsRoomService.getRoomInfo(session);
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        wsRoomService.terminateSession(session);
    }
}















