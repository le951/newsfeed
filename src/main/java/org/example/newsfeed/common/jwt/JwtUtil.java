package org.example.newsfeed.common.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;

@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateAccessToken(Long userId, String nickname, String email) {
        Instant now = Instant.now();

        Map<String, Object> claims = Map.of(
                "sub", email,
                "nickname", nickname,
                "userId", userId,
                "iat", now.getEpochSecond(),
                "exp", now.plusSeconds(jwtProperties.getExpiration().getAccess()).getEpochSecond()
        );

        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(claims)
                .signWith(key)
                .compact();
    }

//    public String generateRefreshToken(){
//
//    }

    public Map<String, Object> validateAccessToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));

        Claims claims;
        try {
            claims = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token).getPayload();
        } catch (SignatureException e) {
            throw e;
//            return null;
        } catch (ExpiredJwtException e) {
            throw e;
//            return null;
        } catch (MalformedJwtException e) {
            throw e;
//            return null;
        } catch (JwtException e) {
            throw e;
//            return null;
        }

        return Map.of(
                "email", claims.getSubject(),
                "nickname", (String) claims.get("nickname"),
                "userId", claims.get("userId")
        );

    }

//    public String validateRefreshToken(String Token){
//
//    }


}
