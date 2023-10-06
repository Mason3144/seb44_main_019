package com.shellwe.back.service;

import com.shellwe.back.dto.ChatRoom;
import com.shellwe.back.mapper.RoomMapper;
import com.shellwe.back.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@org.springframework.stereotype.Service
public class WsRoomService extends Service{
    public Map<Long, ChatRoom> chatRoomSessions = new LinkedHashMap<>();
    public WsRoomService(MemberRoomRepository memberRoomRepository,
                         MemberRepository memberRepository,
                         RoomRepository roomRepository,
                         MessageRepository messageRepository,
                         ShellRepository shellRepository,
                         RoomMapper roomMapper) {
        super(memberRoomRepository, memberRepository, roomRepository, messageRepository, roomMapper,shellRepository);
    }

    public void getRoomInfo(WebSocketSession session) throws IOException {
        long roomId = getRoomId(session);
        joinRoom(session, chatRoomSessions, roomId);
    }

    public void terminateSession(WebSocketSession session){
        long roomId = getRoomId(session);
        chatRoomSessions.get(roomId).removeSession(session);
        if(chatRoomSessions.get(roomId).getSessions().size()==0) chatRoomSessions.remove(roomId);
    }
}
