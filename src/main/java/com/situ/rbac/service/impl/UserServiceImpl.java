package com.situ.rbac.service.impl;

import com.situ.rbac.entity.User;
import com.situ.rbac.mapper.UserMapper;
import com.situ.rbac.service.UserService;
import com.situ.rbac.util.RedisUtil;
import com.situ.rbac.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

/**
 * 为了配合Spring security
 * UserServiceImpl 需要实现一个接口UserDetailsService
 * 用户登录时，默认调用UserDetailsService接口的loadUserByUsername
    进行用户信息的获取
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserMapper userMapper;
    private final RedisUtil redisUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        System.out.println(user);
        if(ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        System.out.println(new BCryptPasswordEncoder().encode("123"));
        return user;
    }

    @Override
    public String login(User user) throws Exception {
        //这时的登录，依赖于Spring Security
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        //登录
        Authentication authentication =
                authenticationManager.authenticate(authToken);
        if(ObjectUtils.isEmpty(authentication)){
            throw new Exception("登录失败！！！");
        }
        //登录成功了
        User user1 = userMapper.selectByUsername(user.getUsername());

        //以前登录成功后，要把登录信息保存在Session中
        //Session需要依赖于Cookie
        //Redis保存登录信息 key:  user::  value(登录信息)

        //生成一个token
        String token = TokenUtil.createToken(user1.getId().toString());

        //将token保存到Redis中
        redisUtil.add("user::"+user1.getId(),token,20, TimeUnit.MINUTES);

        return token;
    }
}
