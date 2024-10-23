package com.back.blog.exception;

import com.back.blog.common.ErrorCode;
import lombok.Getter;

@Getter
public abstract class BlogException  extends RuntimeException{
    private ErrorCode errorCode;

    public BlogException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BlogException(String message) {
        super(message);
    }
}
