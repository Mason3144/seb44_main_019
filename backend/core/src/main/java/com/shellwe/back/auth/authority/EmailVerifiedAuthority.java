package com.shellwe.back.auth.authority;

import org.springframework.security.core.GrantedAuthority;

public class EmailVerifiedAuthority implements GrantedAuthority {

    private boolean emailVerificationStatus;

    public EmailVerifiedAuthority(boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    @Override
    public String getAuthority() {
        if (emailVerificationStatus) {
            return "EMAIL_VERIFIED";
        } else {
            return "EMAIL_NOT_VERIFIED";
        }
    }
}
