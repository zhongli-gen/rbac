package com.situ.rbac.config;

import com.situ.rbac.filter.TokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
/*
@EnableGlobalMethodSecurity 负责开启全局的方法级的Security
    prePostEnabled=true
         启用两个注解
            @PreAuthorize 注解会在方法执行前进行验证。
            @PostAuthorize 注解会在方法执行后进行验证。
 */
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenFilter tokenFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http.formLogin()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();*/
        http.csrf().disable()//关闭跨站请求
                .cors().configurationSource(corsConfigSource())//配置跨域
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//关闭session
/*                .and()
                .formLogin()//提供登录的表单*/
                .and()
                .authorizeRequests()//返回一个URL配置对象
                .antMatchers("/api/user/login")//自己实现的登录接口
                .permitAll()//放行
                .anyRequest()                      // 所有请求
                .authenticated()                   // 需要登录
        ;

                //插入登录过滤器
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    private CorsConfigurationSource corsConfigSource() {
        //配置跨域的信息
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://192.168.110.222:8080");
        configuration.addAllowedOrigin("http://localhost:8080");
        //允许传递Cookie
        configuration.setAllowCredentials(true);
        //允许所有的请求方式
        configuration.addAllowedMethod("*");
        //允许所有的请求头
        configuration.addAllowedHeader("*");
        //允许返回那些响应头
        configuration.addExposedHeader("*");

        //生成配置的源
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        //配置生成的路径
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

    /**
     * 密码的加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
