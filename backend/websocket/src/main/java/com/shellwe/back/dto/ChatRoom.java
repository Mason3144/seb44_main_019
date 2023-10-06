package com.shellwe.back.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ChatRoom {
    private Long id;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoom(Long roomId) {
        this.id = roomId;
    }
    public void setSessions(WebSocketSession session){
        sessions.add(session);
    }
    public void removeSession(WebSocketSession session) {sessions.remove(session);}

}
