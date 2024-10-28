package com.back.blog.user.mapper;

import com.back.blog.user.domain.UserDomain;
import com.back.blog.user.dto.request.SignInReq;
import com.back.blog.user.dto.request.SignUpReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDomain getUser(String userId);

    /**
     * 로그인 성공 여부 체크
     * @param req
     * @return
     */
    UserDomain loginCheck(SignInReq req);

    /**
     * 회원가입
     * @param req
     */
    void addUser(SignUpReq req);

    /**
     * 토큰 재발급을 위한 사용자 패스워드 조회
     * @param userId
     * @return
     */
    String getUserPassword(String userId);
}
