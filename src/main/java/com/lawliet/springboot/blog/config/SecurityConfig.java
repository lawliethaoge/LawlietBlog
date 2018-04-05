package com.lawliet.springboot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 *
 * @author hao@lawliet.com
 * @since 2018/3/15 17:06
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/static/css/**","/static/js/**","/fonts/**","/index").permitAll()//都可以访问
            .antMatchers("/users/**").permitAll() //需要对应角色才能访问
            .and()
            .formLogin()  //基于Form表单登录验证
            .loginPage("/login").failureUrl("/login-error");  //登录界面
    }

    /*
     * 认证信息管理
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
            .inMemoryAuthentication()  //认证信息存储于内存中
                .withUser("hao").password("123").roles("ADMIN");

    }
}
