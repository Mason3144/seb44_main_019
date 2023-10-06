package com.shellwe.back.mapper;

import com.shellwe.back.auth.memberDetails.MemberContextInform;
import com.shellwe.back.dto.MemberDto;
import com.shellwe.back.dto.MessageDto;
import com.shellwe.back.dto.RoomDto;
import com.shellwe.back.entity.Member;
import com.shellwe.back.entity.websocket.MemberRoom;
import com.shellwe.back.entity.websocket.Message;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {
    default MemberDto.Response memberToMemberResponse(Member member){
        return MemberDto.Response.builder()
                .id(member.getId())
                .displayName(member.getDisplayName())
                .profileUrl(member.getProfileUrl())
                .build();
    };
    default MemberDto.Response memberContextToMemberResponse(MemberContextInform member){
        return MemberDto.Response.builder()
                .id(member.getId())
                .displayName(member.getDisplayName())
                .profileUrl(member.getProfileUrl())
                .build();
    };


    default RoomDto.Response memberRoomToWsResponse(MemberRoom memberRoom, long unreadCount, String message, LocalDateTime messageCreatedAt){
        return RoomDto.Response.builder()
                .id(memberRoom.getRoom().getId())
                .unread(unreadCount)
                .lastMessage(message)
                .member(memberToMemberResponse(memberRoom.getMember()))
                .lastMessageCreatedAt(messageCreatedAt)
                .build();
    };
    default MessageDto.Response messageToMessageResponse(Message message, long memberId){
        return MessageDto.Response.builder()
                .createdAt(message.getCreatedAt())
                .payload(message.getPayload())
                .roomId(message.getRoom().getId())
                .member(memberToMemberResponse(message.getMember()))
                .notification(message.isNotification())
                .mine(!message.isNotification() && memberId == message.getMember().getId())
                .build();
    };
}
