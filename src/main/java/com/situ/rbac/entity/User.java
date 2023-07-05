package com.situ.rbac.entity;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
public class User implements UserDetails {
    private Integer id;
    private String username;
    private String password;

    private Set<Role> roles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //获取身份认证，包含他的角色，和他的权限
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        //处理角色
        if(!ObjectUtils.isEmpty(roles)){
            //遍历所有的角色
            for(Role role : roles){
                //创建一个SimpleGrantedAuthority
                SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role.getCode());
                authorities.add(sga);
                if(!ObjectUtils.isEmpty(role.getPermissions())){
                    for(Permission permission : role.getPermissions()){
                        SimpleGrantedAuthority sgay = new SimpleGrantedAuthority(permission.getCode());
                        authorities.add(sgay);
                    }
                }
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        //账号是否过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //账号是否锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //凭证是否过期
        return true;
    }

    @Override
    public boolean isEnabled() {
        //用户是否可用
        return true;
    }
}
