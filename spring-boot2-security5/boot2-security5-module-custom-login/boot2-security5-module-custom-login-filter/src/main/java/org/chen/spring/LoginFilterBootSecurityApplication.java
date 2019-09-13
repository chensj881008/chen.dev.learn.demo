package org.chen.spring;

import org.chen.spring.security.servlet.VerifyCodeServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author chensj
 * @date 2019-09-12 14:42
 */
@SpringBootApplication
public class LoginFilterBootSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginFilterBootSecurityApplication.class, args);
    }

    /**
     * 注册 验证码 Servlet到spring中
     *
     * @return bean
     */
    @Bean
    public ServletRegistrationBean<VerifyCodeServlet> servletServletRegistrationBean() {
        ServletRegistrationBean<VerifyCodeServlet> bean =
                new ServletRegistrationBean<>(new VerifyCodeServlet());
        bean.addUrlMappings("/getVerifyCode");
        return bean;
    }
}
