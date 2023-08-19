package com.shellwe.back.customAnnotation;

import com.shellwe.back.auth.authority.EmailVerifiedAuthority;
import com.shellwe.back.auth.memberDetails.MemberContextInform;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;
import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        MemberContextInform mockUser = mockUserInfo();

        List<EmailVerifiedAuthority> emailVerifiedAuthorities =
                Collections.singletonList(new EmailVerifiedAuthority(true));

        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUser, null, emailVerifiedAuthorities);
        context.setAuthentication(authentication);

        return context;
    }
    private MemberContextInform mockUserInfo(){
        final Long id = 1L;
        final String email = "test@gmail.com";
        final String displayName = "mockUser";
        final String profileUrl = "empty";

        return new MemberContextInform(id, email, displayName, profileUrl);
    }
}