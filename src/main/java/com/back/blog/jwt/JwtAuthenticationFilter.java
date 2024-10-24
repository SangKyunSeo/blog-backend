package com.back.blog.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();
        if(jwt != null && tokenProvider.validateToken(jwt)){
            if(!requestURI.equals("/api/user/reIssue")){
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//
//        if(token != null && token.startsWith("Bearer ")){
//            return token.substring(7);
//        }

        String requestURI = request.getRequestURI();
        Cookie[] cookies = request.getCookies();

        log.debug("<< Request URI >> {}", requestURI);

        if(cookies == null){
            return null;
        }

        if(cookies.length == 0){
            log.info("[cookie 없음]");
            return null;
        }

        if(!requestURI.equals("/api/user/reIssue")){
            // 재발급 요청이 아니면 AccessToken 유효성 검사
            log.debug("<< 기존 요청에 대한 AccessToken 유효성 검사 진행 >>");
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }else{
            log.debug("<< 재발급 요청에 대한 RefreshToken 유효성 검사 진행 >>");
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
