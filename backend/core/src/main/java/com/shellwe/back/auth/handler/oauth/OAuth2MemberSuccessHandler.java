package com.shellwe.back.auth.handler.oauth;

import com.shellwe.back.auth.jwt.JwtTokenizer;
import com.shellwe.back.auth.sevice.OAuthMemberService;
import com.shellwe.back.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenizer jwtTokenizer;
    private final OAuthMemberService oAuthMemberService;
    private final OAuth2MemberFailureHandler failureHandler;

    public OAuth2MemberSuccessHandler(JwtTokenizer jwtTokenizer,
                                      OAuthMemberService oAuthMemberService,
                                      OAuth2MemberFailureHandler failureHandler) {

        this.jwtTokenizer = jwtTokenizer;
        this.oAuthMemberService = oAuthMemberService;
        this.failureHandler = failureHandler;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User principal = (OAuth2User) authentication.getPrincipal();

        String email = String.valueOf(principal.getAttributes().get("email"));
        String displayName = String.valueOf(principal.getAttributes().get("name"));
        String profileUrl = String.valueOf(principal.getAttributes().get("picture"));

        profileUrl = checkProfileUrlNull(profileUrl);
        Member memberByOauth = memberFactory(email, displayName, profileUrl);

        Member memberByDb = oauthSignUpMember(memberByOauth);
        if (memberByDb == null) {
            failureHandler.onAuthenticationFailure(request, response, new AuthenticationException("Oauth failed, Duplicated Email") {});
        } else {
            redirectToken(request, response, memberByDb);
        }
    }

    private String checkProfileUrlNull(String profileUrl) {
        if (profileUrl == null) {
            profileUrl = "empty";
        }
        return profileUrl;
    }

    private Member memberFactory(String email, String displayName, String profileUrl) {
        Member member = Member.builder()
                .email(email)
                .displayName(displayName)
                .profileUrl(profileUrl)
                .build();

        member.emailVerificationCompleted();
        return member;
    }

    private Member oauthSignUpMember(Member member) {
        return oAuthMemberService.oauthSignUpMember(member);
    }

    private void redirectToken(HttpServletRequest request,
                               HttpServletResponse response,
                               Member member) throws IOException {

        String accessToken = delegateAccessToken(member);
        String refreshToken = delegateRefreshToken(member);

        response.setContentType("application/json");

        String uri = createURI(accessToken, refreshToken, member).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);

        log.info("oauth response completed");
    }

    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());
        claims.put("displayName", member.getDisplayName());
        claims.put("emailVerificationStatus", member.getEmailVerificationStatus());
        claims.put("profileUrl", member.getProfileUrl());

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

    private URI createURI(String accessToken, String refreshToken, Member member) {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("Authorization", "Bearer " + accessToken);
        queryParams.add("Refresh", refreshToken);
        queryParams.add("id", member.getId().toString());
        String displayName = member.getDisplayName();
        String encodedDisplayName = URLEncoder.encode(displayName, StandardCharsets.UTF_8);
        queryParams.add("displayName", encodedDisplayName);
        queryParams.add("profileUrl", member.getProfileUrl());

        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("shellwe.net")
                .path("/oauth2/authorization/google/success")
                .queryParams(queryParams)
                .build().toUri();
    }
}
