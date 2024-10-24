package com.back.blog.exception;

import com.back.blog.common.ErrorCode;

public class SignUpException extends BlogException {
    public SignUpException(String message) {
        super(message, ErrorCode.SIGNUP_EXCEPTION);
    }

    public SignUpException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
