package org.chen.spring.security5.boot.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.WebAttributes;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码验证过滤器
 *
 * @author chensj
 * @date 2019-09-12 10:24
 */
@Slf4j
public class VerificationCodeFilter extends OncePerRequestFilter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (isProtectedUrl(request)) {
            String verifyCode = request.getParameter("verifyCode");
            if (!validateVerify(verifyCode)) {
                //手动设置异常
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new DisabledException(
                        "验证码输入错误"));
                // 转发到错误Url
                request.getRequestDispatcher("/login/error").forward(request, response);
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean validateVerify(String inputVerify) {
        //获取当前线程绑定的request对象
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 不分区大小写
        // 这个validateCode是在servlet中存入session的名字
        String validateCode = ((String) request.getSession().getAttribute("validateCode")).toLowerCase();
        inputVerify = inputVerify.toLowerCase();

        log.info("验证码:[{}] 用户输入:[{}]", validateCode, inputVerify);
        return validateCode.equals(inputVerify);
    }

    /**
     * 拦截 /login的POST请求
     *
     * @param request 请求
     * @return boolean
     */
    private boolean isProtectedUrl(HttpServletRequest request) {
        return "POST".equals(request.getMethod()) && pathMatcher.match("/login", request.getServletPath());
    }
}
