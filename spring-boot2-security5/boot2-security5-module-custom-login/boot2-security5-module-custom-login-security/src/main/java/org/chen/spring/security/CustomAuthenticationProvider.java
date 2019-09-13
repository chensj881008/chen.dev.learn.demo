package org.chen.spring.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义登录参数验证实际处理提供者
 *
 * @author chensj
 * @date 2019-09-13 下午11:27
 */
@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 从登陆信息中获取用户信息，主要是用户名和密码信息
        String username = (String) authentication.getPrincipal();
        String password = authentication.getCredentials().toString();

        User user = (User) customUserDetailsService.loadUserByUsername(username);
        // 获取提交的信息
        CustomAuthenticationDetails details = (CustomAuthenticationDetails) authentication.getDetails();
        // 获取验证码
        String verifyCode = details.getVerifyCode();
        // 验证验证码
        if (!validateVerify(verifyCode)) {
            throw new DisabledException("验证码输入错误");
        }

        // 密码验证
        // 如果是自定义AuthenticationProvider，需要手动密码校验
        if(!user.getPassword().equals(password)) {
            throw new BadCredentialsException("密码错误");
        }

        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
    }

    /**
     * 验证码验证
     *
     * @param verifyCode 验证码
     * @return result
     */
    private boolean validateVerify(String verifyCode) {
        //获取当前线程绑定的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 不分区大小写
        // 这个validateCode是在servlet中存入session的名字
        String validateCode = ((String) request.getSession().getAttribute("validateCode")).toLowerCase();
        verifyCode = verifyCode.toLowerCase();

        log.info("验证码：" + validateCode + "  用户输入：" + verifyCode);
        return validateCode.equals(verifyCode);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 这里不要忘记，和UsernamePasswordAuthenticationToken比较
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
