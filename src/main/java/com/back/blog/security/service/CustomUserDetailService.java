package com.back.blog.security.service;

import com.back.blog.security.dto.CustomUserInfoDto;
import com.back.blog.user.domain.UserDomain;
import com.back.blog.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserDomain userDomain = userMapper.getUser(userId);
        if (userDomain == null) {new UsernameNotFoundException("해당하는 유저가 없습니다.");}

        CustomUserInfoDto dto = new CustomUserInfoDto(userDomain.getUserId(), userDomain.getUserName(), userDomain.getUserPw(), "");
        if (userDomain.getUserAuth() == 0) dto.setRole("ROLE_ADMIN");
        else dto.setRole("ROLE_USER");

        return new CustomUserDetails(dto);
    }
}
