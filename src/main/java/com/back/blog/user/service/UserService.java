package com.back.blog.user.service;

import com.back.blog.common.ErrorCode;
import com.back.blog.exception.BlogException;
import com.back.blog.exception.LoginException;
import com.back.blog.exception.TokenException;
import com.back.blog.jwt.TokenProvider;
import com.back.blog.user.domain.UserDomain;
import com.back.blog.user.dto.request.SignInReq;
import com.back.blog.user.dto.request.SignUpReq;
import com.back.blog.user.dto.response.SignInRes;
import com.back.blog.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 로그인 서비스
     * @param signInReq
     * @return
     */
    public SignInRes login(SignInReq signInReq){
        log.info("<< 로그인 서비스 진입 >>");
        log.debug("[로그인] 요청 파라미터 {}", signInReq);

        String userId = signInReq.getUserId();
        String userPw = signInReq.getUserPw();
        UserDomain userDomain = userMapper.getUser(userId);

        if(userDomain == null){
            // 로그인 실패
            throw new LoginException("존재하지 않는 아이디 입니다.", ErrorCode.LOGIN_EXCEPTION);
        }

        if(!passwordEncoder.matches(userPw, userDomain.getUserPw())){ // 비밀번호 불일치
            throw new LoginException("비밀번호가 일치하지 않습니다.", ErrorCode.LOGIN_EXCEPTION);
        }

        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, userPw);

        try{
            Authentication authentication = authenticationManagerBuilder.getObject()
                    .authenticate(authenticationToken);

            // 권한 조회
            String role = userDomain.getUserAuth() == 0 ? "ROLE_ADMIN" : "ROLE_USER";

            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));


            // 인증 객체 재생성 (권한 포함)
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    authorities);
            String accessToken = tokenProvider.generateAccessToken(newAuthentication);
            String refreshToken = tokenProvider.generateRefreshToken(newAuthentication);

            SignInRes res = new SignInRes();
            res.setAccessToken(accessToken);
            res.setRefreshToken(refreshToken);
            res.setRefreshToken("");
            res.setUserAuth(userDomain.getUserAuth());
            res.setUserName(userDomain.getUserName());

            if(userDomain.getUserProfileUrl() != null){
                res.setUserProfileUrl(userDomain.getUserProfileUrl());
                res.setUserProfileUrl(userDomain.getUserProfileName());
            }
            return res;

        }catch(Exception e){
            log.error("<< 로그인 실패 >> {}", e.getMessage());
            throw new TokenException("토큰 발급중 오류 발생", ErrorCode.TOKEN_GENERATE_EXCEPTION);
        }
    }

    /**
     * 회원 가입 서비스
     * @param signUpReq
     * @return
     */
    public boolean signUp(SignUpReq signUpReq){
        UserDomain user = userMapper.getUser(signUpReq.getUserId());
        log.debug("[mapper 결과] {}", userMapper.getUser(signUpReq.getUserId()));
        log.info("[회원가입 아이디 중복 검사 결과] {}", user);
        if(user != null) {
            log.debug("[회원가입 결과 실패 - 아이디 중복]");
            return false;
        }

        SignUpReq encodedReq = new SignUpReq();
        encodedReq.setUserId(signUpReq.getUserId());
        encodedReq.setUserPw(passwordEncoder.encode(signUpReq.getUserPw()));

        userMapper.addUser(encodedReq);

        return true;
    }
}
