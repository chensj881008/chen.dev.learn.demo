package org.chen.spring.security5.boot.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 该接口用于在Spring Security登录过程中对用户的登录信息的详细信息进行填充
 *
 * @author chensj
 * @date 2019-09-12 10:54
 */
@Component("authenticationDetailsSource")
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest,
        WebAuthenticationDetails> {
    /**
     * 该类内容将原本的 WebAuthenticationDetails 替换为了我们的 CustomWebAuthenticationDetails
     *
     * @param request 请求
     * @return d
     */
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new CustomWebAuthenticationDetails(request);
    }
}
