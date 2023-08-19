package com.shellwe.back.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class RoomDto {
    @Builder
    @Getter
    public static class Post{
        @Positive
        private long myShellId;
        @Positive
        private long sellerShellId;
        @Positive
        private long sellerMemberId;
    }

    @Builder
    @Getter
    public static class Response{
        private long id;
        private long unread;
        private String lastMessage;
        private LocalDateTime lastMessageCreatedAt;
        private MemberDto.Response member;
    }
}
