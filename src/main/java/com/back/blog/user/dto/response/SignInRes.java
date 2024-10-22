package com.back.blog.user.dto.response;

import lombok.Data;

@Data
public class SignInRes {

    // Access Token
    private String accessToken;

    // Refresh Token
    private String refreshToken;

    // 사용자 이름
    private String userName;

    // 사용자 권한
    private int userAuth;

    // 사용자 프로필 url
    private String userProfileUrl;

    // 사용자 프로필 이름
    private String userProfileName;
}
