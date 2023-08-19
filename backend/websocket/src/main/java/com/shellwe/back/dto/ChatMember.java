package com.shellwe.back.dto;


import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Builder
public class ChatMember {
    private MemberDto.Response member;
    private WebSocketSession session;
}
