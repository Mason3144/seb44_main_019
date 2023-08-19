package com.shellwe.back.unitTest.auth;

import com.shellwe.back.auth.jwt.JwtTokenizer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JwtTokenizerTest {
    @InjectMocks
    private JwtTokenizer jwtTokenizer;
    @Test
    void getClaimsTest(){
        String base64 = jwtTokenizer.encodeBase64SecretKey("kqnwdjnqwjndljqnwdljqnwdljnqlwdnklqwndklqnwdlknqwkldnqlkwdnqkwldksmamsdkma");

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("displayName", "홍길동");
        claims.put("emailVerificationStatus", true);
        claims.put("profileUrl", "empty");

        String subject = "hgd1@gmail.com";

        Date expiration = jwtTokenizer.getTokenExpiration(30);

        String accessToken = jwtTokenizer.generateAccessToken(claims,subject, expiration, base64);

        Jws<Claims> jws = jwtTokenizer.getClaims(accessToken, base64);

        assertThat(jws.getBody()).isInstanceOf(Map.class);
        assertThat(jws.getBody().get("id")).isEqualTo(claims.get("id"));
        assertThat(jws.getBody().get("displayName")).isEqualTo(claims.get("displayName"));
        assertThat(jws.getBody().get("emailVerificationStatus")).isEqualTo(claims.get("emailVerificationStatus"));
        assertThat(jws.getBody().get("profileUrl")).isEqualTo(claims.get("profileUrl"));
    }
}
