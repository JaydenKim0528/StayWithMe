package kr.co.swm.jwt.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import static io.jsonwebtoken.Jwts.*;

@Component
@Slf4j
public class JWTUtil { /*JWT의 생성 및 검증 처리*/
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    // 키 객체 생성
    private Key getKey() {
        // base64 인코딩을 하지않고, 키를 직접 바이트 배열로 변환
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT를 생성 메서드
    public String create(Map<String, Object> claims, LocalDateTime expireAt) {
        // 임의로 만든 암호키로 key 설정
        var key = getKey();

        // token의 Expire(만기) 시간을 변환
        var _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());
        return builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .claims(claims)
                .expiration(_expireAt) //토큰의 만료 시간을 설정
                .compact(); //JWT를 문자열로 반환
    }

    public void validate(String token) {
        var key = getKey();

        var parser = parser()
                .setSigningKey(key)
                .build();

        try {
            var result = parser.parseSignedClaims(token);
            result.getPayload().forEach((key1, value1) -> log.info("key : {}, value : {}", key1, value1));
        } catch (Exception e) {
            if (e instanceof SignatureException) {
                throw new RuntimeException("JWT Token Invalid Exception");
            } else if (e instanceof ExpiredJwtException) {
                throw new RuntimeException("JWT Token Expired Exception");
            } else {
                throw new RuntimeException("JWT Exception");
            }
        }
    }


}
