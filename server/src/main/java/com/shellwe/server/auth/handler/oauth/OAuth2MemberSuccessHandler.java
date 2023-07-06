package com.shellwe.server.auth.handler.oauth;

import com.shellwe.server.auth.jwt.JwtTokenizer;
import com.shellwe.server.domain.member.entity.Member;
import com.shellwe.server.domain.member.service.OAuthMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenizer jwtTokenizer;
    private final OAuthMemberService oAuthMemberService;

    public OAuth2MemberSuccessHandler(JwtTokenizer jwtTokenizer,
                                      OAuthMemberService oAuthMemberService) {

        this.jwtTokenizer = jwtTokenizer;
        this.oAuthMemberService = oAuthMemberService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();

        String email = String.valueOf(principal.getAttributes().get("email"));
        //이미지는 picture 도메인 구현후 추가
        String displayName = String.valueOf(principal.getAttributes().get("name"));

        Member memberByOauth = memberFactory(email, displayName);
        oauthSignUpMember(memberByOauth);

        Member memberByDb = oAuthMemberService.oauthFindMemberByEmail(email);
        redirectToken(request, response, memberByDb);
    }

    private Member memberFactory(String email, String displayName) {
        Member member = Member.builder()
                .email(email)
                .displayName(displayName)
                .build();

        member.emailVerificationCompleted();
        return member;
    }

    private void oauthSignUpMember(Member member) {
        oAuthMemberService.oauthSignUpMember(member);
    }

    private void redirectToken(HttpServletRequest request,
                               HttpServletResponse response,
                               Member member) throws IOException {

        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());

        String bodyContent = "OAuth2 토큰이 성공적으로 생성되었습니다.";
        response.getWriter().write(bodyContent);

        log.info("oauth response completed");
    }

    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());
        claims.put("displayName", member.getDisplayName());
        claims.put("emailVerificationStatus", member.getEmailVerificationStatus());
        //picture는 도메인 생성후 구현완료하면 추가하겠습니다.

        String subject = member.getEmail();
        Date expirations = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expirations, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expirations = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expirations, base64EncodedSecretKey);

        return refreshToken;
    }
}
