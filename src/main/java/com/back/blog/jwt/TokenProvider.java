package com.back.blog.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider {
    private final Key key;

    @Value("${jwt.access_time}")
    private Long accessTokenExpire;

    @Value("${jwt.refresh_time}")
    private Long refreshTokenExpire;

    // 초기화 -> 암호화된 key 생성
    public TokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.access_time}") Long accessTokenExpire, @Value("${jwt.refresh_time}") Long refreshTokenExpire) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpire = accessTokenExpire;
        this.refreshTokenExpire = refreshTokenExpire;
    }

    // Access Token 생성
    public String generateAccessToken(Authentication authentication) {
        log.info("<< Access Token 발급 진입 >>");

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date accessTokenExpireTime = new Date(now + accessTokenExpire);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(accessTokenExpireTime)
                .setIssuedAt(new Date())
                .compact();
    }

    // Token에서 유저 정보 추출 후 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        log.info("<< 토큰 정보 추출 및 객체 생성 진입 >>");

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // Token 유효성 검사
    public Boolean validateToken(String token){
        log.info("<< 토큰 검증 메서드 진입 >>");
        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(SignatureException e){
            log.info("SignatureException (서명 오류 토큰) = " + e.getMessage());
            throw new JwtException("SIGNATURE_ERROR");
        }catch(MalformedJwtException e){
            log.info("MalformedJwtException (손상된 토큰) = " + e.getMessage());
            throw new JwtException("MALFORMED_ERROR");
        }catch(ExpiredJwtException e){
            log.info("ExpiredJwtException (만료된 토큰) = " + e.getMessage());
            throw new JwtException("EXPIRED_ERROR");
        }catch(IllegalArgumentException e){
            log.info("IllegalArgumentException (적절하지 않은 파라미터 에러) = " + e.getMessage());
            throw new JwtException("ILLEGAL-ARGUMENT_ERROR");
        }
    }
}
