package com.situ.rbac.filter;


import com.situ.rbac.entity.User;
import com.situ.rbac.mapper.UserMapper;
import com.situ.rbac.service.UserService;
import com.situ.rbac.util.RedisUtil;
import com.situ.rbac.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    private final RedisUtil redisUtil;
    private final UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 相当于Filter doFilter
        // 获取Token
        String strToken = request.getHeader("token");
        if (!StringUtils.hasText(strToken)){
            // 当客户端没有传递Token时，直接放行
            filterChain.doFilter(request, response);
            return ;
        }

        // 客户端传递了Token
        // 1. 解析Token, 取出登录用户的ID
        Integer userId = null;
        try {
            TokenUtil.Token token = TokenUtil.parseToken(strToken);
            userId = Integer.valueOf(token.getCode());    // 用户的ID
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("无效的Token");
        }

        // 2. 再根据用户ID，查询redis, 如果没有，登录超时了
        String rToken = redisUtil.get("user::"+userId);
        if (!StringUtils.hasText(rToken)){
            throw new RuntimeException("登录超时！");
        }

        // 3. 将这个用户的信息，放到Security的上下文中
        User user = userMapper.selectById(userId);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
