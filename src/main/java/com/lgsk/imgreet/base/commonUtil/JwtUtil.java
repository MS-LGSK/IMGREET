package com.lgsk.imgreet.base.commonUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtil {

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    public String generateGreetToken(Long greetId) {
        return Jwts.builder()
                .subject(greetId.toString())
                .claim("greetId", greetId)
                .expiration(null)
                .signWith(new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256"))
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256"))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
}
