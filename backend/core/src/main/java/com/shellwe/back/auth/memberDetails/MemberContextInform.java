package com.shellwe.back.auth.memberDetails;

import lombok.Getter;

@Getter
public class MemberContextInform {

    private Long id;
    private String email;
    private String displayName;
    private String profileUrl;

    public MemberContextInform(Long id, String email, String displayName, String profileUrl) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.profileUrl = profileUrl;
    }
}
