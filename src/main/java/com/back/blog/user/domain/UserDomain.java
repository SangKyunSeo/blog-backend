package com.back.blog.user.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserDomain {
    private int userNum;
    private String userId;
    private String userPw;
    private String userName;
    private int userAuth;
    private String userProfileUrl;
    private String userProfileName;
    private Date userRegDate;
}
