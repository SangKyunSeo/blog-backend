package com.back.blog.common;

import com.back.blog.exception.LoginException;
import com.back.blog.exception.SignUpException;
import com.back.blog.exception.TokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ApiResponse<Object> handleBadRequest(BindException e) {
        log.error(e.getMessage());
        return ApiResponse.error(ErrorCode.BAD_REQUEST_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(LoginException.class)
    protected ApiResponse<Object> handleLoginException(LoginException e) {
        log.error(e.getMessage());
        return ApiResponse.error(ErrorCode.LOGIN_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SignUpException.class)
    protected ApiResponse<Object> handleSignUpException(SignUpException e) {
        log.error(e.getMessage());
        return ApiResponse.error(e.getErrorCode());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenException.class)
    protected ApiResponse<Object> handleTokenException(TokenException e) {
        log.error(e.getMessage());
        return ApiResponse.error(e.getErrorCode());
    }

}
