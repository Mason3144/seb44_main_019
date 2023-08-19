package com.shellwe.back.auth.memberDetails;

import com.shellwe.back.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class MemberDetails extends Member implements UserDetails {

    public MemberDetails(Member member) {
        super(member.getId(),
                member.getEmail(),
                member.getEmailVerificationStatus(),
                member.getPassword(),
                member.getDisplayName(),
                member.getProfileUrl()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
