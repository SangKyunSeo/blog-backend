package com.back.blog.jwt;

import com.back.blog.common.ErrorCode;
import com.back.blog.exception.TokenException;
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
import java.util.Enumeration;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();
        try{
            if(jwt != null && tokenProvider.validateToken(jwt)){
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }catch(TokenException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 상태 코드 401
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    "{\"data\": null, \"code\": \"" + ErrorCode.TOKEN_EXPIRE_EXCEPTION + "\", \"message\": \"토큰이 만료되었습니다.\"}"
            );
        }
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
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder("Request Headers:\n");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.append(headerName).append(": ").append(headerValue).append("\n");
        }
        log.debug("<< Request Headers >> {}", headers);

        if(cookies == null){
            log.info("[Cookie 없음]");
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
