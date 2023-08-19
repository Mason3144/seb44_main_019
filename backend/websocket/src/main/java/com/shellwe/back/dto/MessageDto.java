package com.shellwe.back.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public class MessageDto {
    @Builder
    @Getter
    @Setter
    public static class Response{
        private Long roomId;
        private String payload;
        private boolean mine;
        private boolean notification;
        private LocalDateTime createdAt;
        private MemberDto.Response member;
    }
}
