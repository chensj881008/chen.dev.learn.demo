package org.chen.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author chensj
 * @date 2019-09-14 下午9:38
 */
@EnableRedisHttpSession
@SpringBootApplication
public class LoginShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginShareApplication.class, args);
    }
}
