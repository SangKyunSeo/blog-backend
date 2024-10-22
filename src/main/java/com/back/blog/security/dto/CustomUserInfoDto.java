package com.back.blog.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserInfoDto {
    private String userId;
    private String userName;
    private String userPw;
    private String role;
}
