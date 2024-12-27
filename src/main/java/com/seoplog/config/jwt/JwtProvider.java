package com.seoplog.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${seoplog.jwt-key}")
    private String secretKey;
    private SecretKey key;

    // 만료시간
    public static final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 30; // 30 minutes
    public static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 days

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    /**
     * Access Token 생성
     * @param username 사용자 ID
     * @return Access Token
     */
    public String generateAccessToken(String username) {
        return createToken(username, ACCESS_TOKEN_EXPIRATION);
    }

    /**
     * Refresh Token 생성
     * @param username 사용자 ID
     * @return Refresh Token
     */
    public String generateRefreshToken(String username) {
        return createToken(username, REFRESH_TOKEN_EXPIRATION);
    }

    /**
     * JWT 생성
     * @param username 사용자 ID
     * @param expiration 만료 시간
     * @return JWT
     */
    public String createToken(String username, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * JWT 검증
     * @param token JWT
     * @return 유효성 여부
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("JWT 토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            throw new IllegalArgumentException("JWT 토큰은 지원되지 않습니다.");
        } catch (MalformedJwtException e) {
            throw new IllegalArgumentException("JWT 토큰의 형식이 잘못되었습니다.");
        } catch (SecurityException e) {
            throw new IllegalArgumentException("JWT signature 유효성 검사에 실패했습니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims 문자열이 비어 있습니다.");
        }
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}
