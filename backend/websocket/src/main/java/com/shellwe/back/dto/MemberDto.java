package com.shellwe.back.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberDto {
    @Builder
    @Getter
    public static class Response{
        private long id;
        private String displayName;
        private String profileUrl;
    }
}
