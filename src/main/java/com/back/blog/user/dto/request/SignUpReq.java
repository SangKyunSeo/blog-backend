package com.back.blog.user.dto.request;

import lombok.Data;

@Data
public class SignUpReq {
    private String userId;
    private String userPw;
}
