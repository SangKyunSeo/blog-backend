package com.back.blog.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    BAD_REQUEST_EXCEPTION("C400", "잘못된 요청입니다."),
    LOGIN_EXCEPTION("C001", "아이디 및 비밀번호가 일치하지 않습니다."),

    TOKEN_EXCEPTION("C002", "토큰 검토중 알 수 없는 에러 발생"),
    TOKEN_GENERATE_EXCEPTION("C003", "토큰 발급 도중 오류가 발생했습니다."),
    TOKEN_EXPIRE_EXCEPTION("C004", "토큰이 만료되었습니다."),
    TOKEN_MALFORMED_EXCEPTION("C005", "토큰이 손상되었습니다."),
    TOKEN_SIGNATURE_EXCEPTION("C006", "토큰의 서명이 오류 발생"),

    SIGNUP_DUPLICATE_EXCEPTION("C007", "이미 가입된 아이디입니다."),
    SIGNUP_EXCEPTION("C008", "회원가입 중 알 수 없는 오류 발생" );

    private final String code;
    private final String message;
}
