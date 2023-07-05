package com.situ.rbac.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 设置全局的跨域请求允许
 */
//@Configuration
public class CorsConfig {
    /**
     *创建一个CorsFilter,替换掉Spring Web默认的CorsFilter
     */
    //@Bean
    public CorsFilter corsFilter(){
        //配置跨域的信息
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://127.0.0.1:5500");
        configuration.addAllowedOrigin("http://localhost:5500");
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
        return new CorsFilter(source);
    }

}
