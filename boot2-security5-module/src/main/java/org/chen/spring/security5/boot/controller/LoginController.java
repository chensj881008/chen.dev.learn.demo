package org.chen.spring.security5.boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录Controller
 *
 * @author chensj
 * @date 2019-09-11 15:44
 */
@Controller
public class LoginController {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/")
    public String showHome() {
        // 获取当前用户信息
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("当前登陆用户：" + name);

        return "home.html";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login.html";
    }

    //@RequestMapping("/admin")
    //@ResponseBody
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    //public String printAdmin() {
    //    return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    //}

    //@RequestMapping("/user")
    //@ResponseBody
    //@PreAuthorize("hasRole('ROLE_USER')")
    //public String printUser() {
    //    return "如果你看见这句话，说明你有ROLE_USER角色";
    //}

    /**
     * 登录异常
     * 从session获取异常信息，直接输出到页面上面
     *
     * @param request  请求
     * @param response 响应
     */
    @RequestMapping("/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        //  session 中的 SPRING_SECURITY_LAST_EXCEPTION
        AuthenticationException exception =
                (AuthenticationException) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        try {
            response.getWriter().write(exception.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin','r')")
    public String printAdminRead() {
        return "如果你看见这句话，说明你访问/admin路径具有r权限";
    }

    @RequestMapping("/admin/create")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin','c')")
    public String printAdminCreate() {
        return "如果你看见这句话，说明你访问/admin路径具有create权限";
    }

    @RequestMapping("/admin/update")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin','u')")
    public String printAdminUpdate() {
        return "如果你看见这句话，说明你访问/admin路径具有create权限";
    }

    @RequestMapping("/admin/delete")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin','d')")
    public String printAdminDelete() {
        return "如果你看见这句话，说明你访问/admin路径具有delete权限";
    }
}
