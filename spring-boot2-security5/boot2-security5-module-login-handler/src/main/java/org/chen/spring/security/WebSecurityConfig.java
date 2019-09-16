package org.chen.spring.security;

import org.chen.spring.security.handler.CustomAuthenticationFailureHandler;
import org.chen.spring.security.handler.CustomAuthenticationSuccessHandler;
import org.chen.spring.security.strategy.CustomExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author chensj
 * @date 2019-09-12 15:00
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                // 指定用户登录使用的获取用户信息的服务
                .userDetailsService(userDetailsService)
                // 设置密码加密方式
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence rawPassword) {
                        return rawPassword.toString();
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        return encodedPassword.equals(rawPassword.toString());
                    }
                });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/login/invalid").permitAll()
                // 指定所有访问都是需要登录才尅操作
                .anyRequest().authenticated()
                // 指定采用formLogin方式认证，，登录成功页面 / 并且/login页面不需要验证登录
                .and().formLogin()
                // 设置登录页面为/login
                .loginPage("/login")
                // 设置认证成功后Url， 认证失败后的页面
                //.defaultSuccessUrl("/").failureForwardUrl("/login/error")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                // 指定退出也不需要验证登录
                .and().logout().permitAll()
                .and()
                // 使用session管理 并指定session过期后跳转页面
                .sessionManagement().invalidSessionUrl("/login/invalid")
                // 限制同时登录的人数，指定最大登录人数
                .maximumSessions(1)
                // 当达到最大值时，是否保留已经登录的用户
                .maxSessionsPreventsLogin(true)
                // 当达到最大值时，旧用户被踢出后的操作
                .expiredSessionStrategy(new CustomExpiredSessionStrategy());
        // 关闭csrf 跨域
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/js/**", "/css/**", "/static/**");
    }
}
