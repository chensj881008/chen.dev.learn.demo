package org.chen.spring.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 该接口用于在Spring Security登录过程中对用户的登录信息的详细信息进行填充
 *
 * @author chensj
 * @date 2019-09-13 下午11:16
 */
@Component
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    /**
     * 使用自定义认证信息构建器来实现自定义认证信息的构建
     *
     * @param request 请求
     * @return 认证信息
     */
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new CustomAuthenticationDetails(request);
    }
}
