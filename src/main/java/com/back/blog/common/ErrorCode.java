package com.back.blog.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    BAD_REQUEST_EXCEPTION("C400", "잘못된 요청입니다.");

    private final String code;
    private final String message;
}
