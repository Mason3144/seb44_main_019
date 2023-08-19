package com.shellwe.back.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindResponseDto {

    @JsonProperty("isMe")
    private boolean isMe;

    private Long id;

    private String displayName;

    private String profileUrl;

    public void setIsMeIdName(boolean isMe, Long id, String displayName) {
        this.isMe = isMe;
        this.id = id;
        this.displayName = displayName;
    }
}
