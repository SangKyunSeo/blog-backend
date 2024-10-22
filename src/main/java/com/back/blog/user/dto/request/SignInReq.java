package com.back.blog.user.dto.request;

import lombok.Data;

@Data
public class SignInReq {

    // 사용자 ID
    private String userId;

    // 사용자 PW
    private String userPw;
}
