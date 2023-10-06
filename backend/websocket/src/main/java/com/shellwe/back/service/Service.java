package com.shellwe.back.service;


import com.shellwe.back.auth.memberDetails.MemberContextInform;
import com.shellwe.back.dto.ChatRoom;
import com.shellwe.back.dto.MemberDto;
import com.shellwe.back.entity.*;
import com.shellwe.back.entity.websocket.MemberRoom;
import com.shellwe.back.entity.websocket.Room;
import com.shellwe.back.exception.businessLogicException.BusinessLogicException;
import com.shellwe.back.exception.businessLogicException.ExceptionCode;
import com.shellwe.back.mapper.RoomMapper;
import com.shellwe.back.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@org.springframework.stereotype.Service
public abstract class Service {
    protected final MemberRoomRepository memberRoomRepository;
    protected final MemberRepository memberRepository;
    protected final RoomRepository roomRepository;
    protected final MessageRepository messageRepository;
    protected final RoomMapper roomMapper;
    protected final ShellRepository shellRepository;
    protected Member findExistsMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
    protected com.shellwe.back.entity.websocket.MemberRoom findExistsMemberRoom(long roomId, long memberId){
        Optional<com.shellwe.back.entity.websocket.MemberRoom> optionalMemberRoom = memberRoomRepository.findByRoomAndMemberAndActiveTrue(new Room(roomId), new Member(memberId));
        return optionalMemberRoom.orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMBER_ROOM_NOT_FOUND));
    }
    protected Shell findExistsShell(long shellId){
        Optional<Shell> optionalShell = shellRepository.findById(shellId);
        return optionalShell.orElseThrow(()-> new BusinessLogicException(ExceptionCode.SHELL_NOT_FOUND));
    }

    protected void verifyExistsMemberRoom(long myShellId, long traderShellId){
        Optional<MemberRoom> memberRoom = memberRoomRepository.findFirstByMyShellIdAndTraderShellId(myShellId,traderShellId);

        memberRoom.ifPresent((mr)->{
            if(mr.isActive()) throw new BusinessLogicException(ExceptionCode.MEMBER_ROOM_EXISTS);
        });
    }

    protected ChatRoom joinRoom(WebSocketSession session, Map<Long, ChatRoom> chatRooms,long roomId){
        if(chatRooms.containsKey(roomId)){
            chatRooms.get(roomId).setSessions(session);
        }else {
            ChatRoom chatRoom = new ChatRoom(roomId);
            chatRoom.setSessions(session);
            chatRooms.put(roomId, chatRoom);
        }
        return chatRooms.get(roomId);
    }

    protected MemberDto.Response getMemberResponse(WebSocketSession session){
        Authentication authentication = (Authentication) session.getPrincipal();
        MemberContextInform member =  (MemberContextInform) authentication.getPrincipal();
        MemberDto.Response memberResponse = roomMapper.memberContextToMemberResponse(member);
        return memberResponse;
    }
    protected long getLoggedInMemberId(){
        try{
            Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
            MemberContextInform member =  (MemberContextInform) authentication.getPrincipal();
            return member.getId();
        }catch (Exception e){
            throw new BusinessLogicException(ExceptionCode.LOGIN_NEEDED);
        }
    }
    protected long getRoomId(WebSocketSession session){
        try{
            String roomId = Arrays.stream(session.getUri().getQuery().split("&")).filter(s->s.startsWith("roomId"))
                    .collect(Collectors.toList())
                    .get(0)
                    .replace("roomId=","");
            return Long.parseLong(roomId);
        }catch(Exception e){
            throw new BusinessLogicException(ExceptionCode.WRONG_ROOM_NUMBER);
        }
    }
}
