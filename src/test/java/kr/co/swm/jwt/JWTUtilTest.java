package kr.co.swm.jwt;

import kr.co.swm.jwt.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Slf4j
class JWTUtilTest {
    @Autowired
    JWTUtil jwtUtil;

    @Test
    void create() {
        // JWT 생성
        var claims = new HashMap<String, Object>();
        claims.put("user_id", "devocean");

        // 토큰 만료 시간 설정
        var expiredAt = LocalDateTime.now().plusMinutes(10);

        // JWT 토큰 생성
        String jwtToken = jwtUtil.create(claims, expiredAt);

        // 생성된 토큰이 null이 아니어야 함
        assert jwtToken != null : "생성된 토큰 null 아님";

        // JWT 검증
        assertDoesNotThrow(() -> jwtUtil.validate(jwtToken), "토큰 검증 예외 체크");
        // 생성된 토큰을 로그에 출력
        log.info("생성된 토큰: {}", jwtToken);
    }

    @Test
    void validate() {
        // 위에서 생성한 토큰을 이곳에 복사해서 넣어야 합니다.
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZGV2b2NlYW4iLCJleHAiOjE3MjMxODk5OTl9.RwggmguHichMddwe4Vogxwl7FEhFfyl9UnUlO0ejQNw";

        // validate 메서드 호출 시 예외가 발생하지 않아야 합니다.
        assertDoesNotThrow(() -> jwtUtil.validate(token), "Token validation should not throw any exception");

        log.info("토큰 검증이 성공적으로 끝났음");
    }
}