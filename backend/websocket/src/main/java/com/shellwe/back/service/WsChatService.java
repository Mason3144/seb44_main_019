package com.shellwe.back.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shellwe.back.dto.*;
import com.shellwe.back.entity.Member;
import com.shellwe.back.entity.websocket.*;
import com.shellwe.back.exception.businessLogicException.BusinessLogicException;
import com.shellwe.back.exception.businessLogicException.ExceptionCode;
import com.shellwe.back.mapper.RoomMapper;
import com.shellwe.back.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class WsChatService extends com.shellwe.back.service.Service {
    private final ObjectMapper objectMapper;
    private final AsyncService asyncService;
    private final WsRoomService wsRoomService;
    private static Map<Long, ChatRoom> chatMemberSessions = new LinkedHashMap<>();;
    public WsChatService(MemberRoomRepository memberRoomRepository,
                         MemberRepository memberRepository,
                         RoomRepository roomRepository,
                         MessageRepository messageRepository,
                         RoomMapper roomMapper,
                         ShellRepository shellRepository,
                         ObjectMapper objectMapper,
                         AsyncService asyncService,
                         WsRoomService wsRoomService) {
        super(memberRoomRepository, memberRepository, roomRepository, messageRepository, roomMapper,shellRepository);
        this.objectMapper = objectMapper;
        this.asyncService = asyncService;
        this.wsRoomService = wsRoomService;
    }

    public void handleMessage(WebSocketSession session, TextMessage message) throws IOException {
        long roomId = getRoomId(session);
        MemberDto.Response member = getMemberResponse(session);
        asyncService.saveMessage(chatMemberSessions, message, roomId, member.getId());
        sendMessage(session, message, member, roomId);

//읽지않은 메세지 정보 표시 기능(보류하기로 결정)
//        sendRoomInfo(session, message, roomId);
    }
    private void sendRoomInfo(WebSocketSession session, TextMessage message, long roomId){
        if(chatMemberSessions.get(roomId).getSessions().size()==1){
            ChatRoom chatRoom = wsRoomService.chatRoomSessions.get(roomId);
            MemberDto.Response loggedInMember = getMemberResponse(session);

            chatRoom.getSessions().stream().filter(s->
                    getMemberResponse(s).getId() != loggedInMember.getId()
            ).forEach(s->{
                RoomDto.Response response = RoomDto.Response.builder()
                        .id(roomId)
                        .member(loggedInMember)
                        .unread(messageRepository.unReadCount(roomId,getMemberResponse(s).getId())+1)
                        .lastMessage(message.getPayload())
                        .build();
                try {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    private void sendMessage(WebSocketSession session, TextMessage message, MemberDto.Response member, long roomId) throws IOException {
        ChatRoom chatRoom = chatMemberSessions.get(roomId);

        chatRoom.getSessions().forEach(sessions->{
            MessageDto.Response response = MessageDto.Response.builder()
                    .roomId(roomId)
                    .payload(message.getPayload())
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime())
                    .member(member)
                    .build();
            if(sessions.equals(session)) response.setMine(true);
            try {
                sessions.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void terminateSession(WebSocketSession session){
        long roomId = getRoomId(session);

        chatMemberSessions.get(roomId).removeSession(session);
        if(chatMemberSessions.get(roomId).getSessions().size()==0) chatMemberSessions.remove(roomId);
    }

    public void getPreviousMessages(WebSocketSession session) throws IOException {
        long roomId = getRoomId(session);
        MemberDto.Response member = getMemberResponse(session);

        joinRoom(session, chatMemberSessions,roomId);
        findExistsMemberRoom(session,roomId,member.getId());

        List<MessageDto.Response> responses = getMessageResponse(roomId, member.getId());

        responses.forEach(r-> {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(r)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<MessageDto.Response> getMessageResponse(long roomId, long memberId){
        List<Message> messages = messageRepository.findAllByRoomOrderByIdAsc(new Room(roomId));

        List<MessageDto.Response> responses = messages.stream()
                .map(message->{
                    modifyUnreadMessage(message,memberId);
                    return roomMapper.messageToMessageResponse(message, memberId);
                })
                .collect(Collectors.toList());

        return responses;
    }

    private void modifyUnreadMessage(Message message, long memberId){
        if(!message.isNotification() && message.getMember().getId()!=memberId && message.isUnread()){
            message.setUnread(false);
            messageRepository.save(message);
        }
    }

    private MemberRoom findExistsMemberRoom(WebSocketSession session, long roomId, long memberId) throws IOException {
        Optional<MemberRoom> optionalMemberRoom = memberRoomRepository.findByRoomAndMemberAndActiveTrue(new Room(roomId), new Member(memberId));
        if(optionalMemberRoom.isEmpty()) {
            MessageDto.Response message = MessageDto.Response.builder()
                    .notification(true)
                    .roomId(roomId)
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime())
                    .payload("해당 채팅방의 접근권한이 없습니다.")
                    .build();
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            throw new BusinessLogicException(ExceptionCode.MEMBER_ROOM_NOT_FOUND);
        }else return optionalMemberRoom.get();
    }

}
