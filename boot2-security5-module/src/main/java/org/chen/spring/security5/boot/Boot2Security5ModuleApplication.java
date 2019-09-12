package org.chen.spring.security5.boot;

import org.chen.spring.security5.boot.servlet.VerificationCodeServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author chensj
 */
@SpringBootApplication
public class Boot2Security5ModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot2Security5ModuleApplication.class, args);
    }

    /**
     * 注入验证码servlet
     *
     * @return servlet
     */
    @Bean
    public ServletRegistrationBean<VerificationCodeServlet> verificationCodeServlet() {
        ServletRegistrationBean<VerificationCodeServlet> registrationBean =
                new ServletRegistrationBean<>(new VerificationCodeServlet());
        registrationBean.addUrlMappings("/getVerificationCode");
        return registrationBean;
    }
}
