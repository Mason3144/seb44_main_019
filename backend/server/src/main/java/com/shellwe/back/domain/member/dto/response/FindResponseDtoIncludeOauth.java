package com.shellwe.back.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindResponseDtoIncludeOauth {

    @JsonProperty("isMe")
    private boolean isMe;

    private Long id;

    private String displayName;

    private boolean oauthUser = false;

    private String profileUrl;

    private String introduction;

    public void setIsMeIdName(boolean isMe, Long id, String displayName, String profileUrl, String introduction) {
        this.isMe = isMe;
        this.id = id;
        this.displayName = displayName;
        this.profileUrl = profileUrl;
        this.introduction = introduction;
    }
}
