package org.chen.spring.security4.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功后的自定义处理逻辑
 * 实现{@link org.springframework.security.web.authentication.AuthenticationSuccessHandler} 接口
 *
 * @author chensj
 * @date 2019-9-8 17:17:07
 */
public class SpringAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * jackson 提供工具类，用于转换对象到json字符串
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 登录成功后的处理逻辑
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 代表认证成功后的信息
     * @throws IOException      异常
     * @throws ServletException 异常
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 返回登录成功json给前端
        Map map = new HashMap(8);
        map.put("success", true);
        String json = objectMapper.writeValueAsString(map);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
