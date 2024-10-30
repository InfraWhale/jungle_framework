package com.jungle.jungleSpring.jwt;

import com.jungle.jungleSpring.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    
    //// 토큰 생성에 필요한 값들
    
    // 토큰을 헤더에서 가져올 때 사용할 키
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값을 저장할 때 사용할 키
    public static final String AUTHORIZATION_KEY = "auth";
    // 토큰 식별자 : Bearer"라는 문자열을 토큰 앞에 붙여 토큰을 구분
    private static final String BEARER_PREFIX = "Bearer";
    // 토큰 만료시간 : 토큰의 만료 시간(1시간)을 밀리초 단위로 정의
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    // application.yml 파일에서 가져오는 비밀 키 값
    @Value("${jwt.secret.key}")
    private String secretKey;
    // secretKey를 암호화 키로 변환한 Key 객체
    private Key key;
    // 서명에 사용할 알고리즘을 HS256으로 지정
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    
    // 비밀키를 암호화 키로 변환하여 key에 저장
    @PostConstruct
    public void init() {
        try {
            byte[] bytes = Base64.getDecoder().decode(secretKey);
            key = Keys.hmacShaKeyFor(bytes);
            log.info("Secret key initialization successful.");
        } catch (IllegalArgumentException e) {
            log.error("Invalid secret key provided in application.yml. Check if it's Base64 encoded.", e);
        }
    }
    
    //// Header에서 토큰 가져오기 : Authorization 헤더에서 JWT를 가져옴
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        // "Bearer"로 시작하는지 확인한다.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            // 맞으면 토큰을 가져온다.
            //return bearerToken.substring(7); // 틀린 코드
            return bearerToken.substring(BEARER_PREFIX.length()).trim(); // 이렇게 고칠 것
        }
        return null;
    }

    //// 토큰 생성 : 주어진 사용자 이름과 역할을 기반으로 JWT를 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        String token = Jwts.builder()
                .setSubject(username)
                .claim(AUTHORIZATION_KEY, role)
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();

        log.info("Generated Token: {}", token);  // 생성된 토큰을 로그에 출력
        return BEARER_PREFIX + token;
    }

    //// 토큰 유효성 검증
    public boolean validateToken(String token) {
        log.info("Validating Token: {}", token);  // 검증하려는 토큰을 로그에 출력
        try {
            // 서명과 만료 여부를 검증한다.
            // parseClaimsJws : JWT의 유효한 서명과 함께 페이로드(Claims)를 포함하고 있으면, 해당 클레임 정보를 추출하여 반환한다.
            // 클레임 : JWT 내부에 포함된 데이터의 조각들로, 토큰을 통해 인증 및 권한 부여에 필요한 정보를 담고 있다.
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    //// 토큰에서 사용자 정보 가져오기 : 토큰의 클레임 객체를 반환한다.
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    
}
