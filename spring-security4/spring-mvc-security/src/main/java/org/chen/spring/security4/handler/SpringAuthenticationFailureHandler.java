package org.chen.spring.security4.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录失败自定义处理逻辑
 * 实现@{@link org.springframework.security.web.authentication.AuthenticationFailureHandler}
 *
 * @author chensj
 * @date 2019-09-08 17-20 17:20
 */
public class SpringAuthenticationFailureHandler implements AuthenticationFailureHandler {
    /**
     * jackson 提供工具类，用于转换对象到json字符串
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 登录失败处理逻辑
     *
     * @param request   请求
     * @param response  响应
     * @param exception 异常
     * @throws IOException      异常
     * @throws ServletException 异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 前端登录失败处，返回json给前端
        Map map = new HashMap();
        map.put("success", false);
        String json = objectMapper.writeValueAsString(map);
        response.getWriter().write(json);
    }
}
