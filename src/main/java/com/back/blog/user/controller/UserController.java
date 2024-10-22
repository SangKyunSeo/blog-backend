package com.back.blog.user.controller;

import com.back.blog.common.ApiResponse;
import com.back.blog.user.domain.UserDomain;
import com.back.blog.user.dto.request.SignInReq;
import com.back.blog.user.dto.request.SignUpReq;
import com.back.blog.user.dto.response.SignInRes;
import com.back.blog.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    // 로그인
    @PostMapping("/signIn")
    public ApiResponse<SignInRes> login(@RequestBody SignInReq req){
        log.debug("<< 로그인 컨트롤러 진입 >>");
        log.info("[요청 파라미터] {}", req);
        SignInRes res = userService.login(req);

        log.info("[로그인 결과] {}", res);
        return ApiResponse.success(res);
    }

    // 회원가입
    @PostMapping("/signUp")
    public ApiResponse<String> signUp(@RequestBody SignUpReq req){
        log.info("<< 회원 가입 컨트롤러 진입 >> {}", req);
        boolean result = userService.signUp(req);
        log.debug("[회원가입 결과] {}", result);
        return ApiResponse.success();
    }




}
