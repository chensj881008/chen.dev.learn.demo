package com.winning.devops.springboot.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Lenovo
 * @description  @SpringBootApplication 注解包含以下这些注解
 * @description @Configuration 将class 标记为应用程序上下文的bean定义的源。
 * @description @EnableAutoConfiguration 告诉Spring Boot根据类路径设置、其他bean和各种属性设置开始添加bean。
 *              通常，您会为Spring MVC应用程序添加@EnableWebMvc，但是Spring Boot在类路径中看到Spring-webmvc时会自动添加@EnableWebMvc。
 *              这将应用程序标记为web应用程序，并激活关键行为，如设置DispatcherServlet。
 * @description @ComponentScan tells Spring to look for other components, configurations, and services in the hello package, allowing it to find the controllers.
 * @description @@ComponentScan 告诉Spring在hello包中查找其他组件、配置和服务，从而允许它查找控制器。
 */
@SpringBootApplication
public class SpringBootWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebsocketApplication.class, args);
    }

}

