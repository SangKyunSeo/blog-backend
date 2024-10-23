package com.back.blog.exception;

import com.back.blog.common.ErrorCode;

public class LoginException extends BlogException{
    public LoginException(String message) {
        super(message, ErrorCode.LOGIN_EXCEPTION);
    }

    public LoginException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
