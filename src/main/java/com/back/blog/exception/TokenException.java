package com.back.blog.exception;

import com.back.blog.common.ErrorCode;

public class TokenException extends BlogException{
    public TokenException(String message){
        super(message, ErrorCode.TOKEN_EXCEPTION);
    }

    public TokenException(String message, ErrorCode errorCode){
        super(message, errorCode);
    }
}
