package com.winning.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author chensj
 * @title Eureka 注册中心
 * @project spring-cloud-eureka-server
 * @package com.winning.devops
 * @date: 2019-02-03 21:34
 */
@SpringBootApplication
@EnableEurekaServer
public class AppEureka {

    // @EnableEurekaServer 表示开启 EurekaServer服务 开启注册中心

    public static void main(String[] args){
        SpringApplication.run(AppEureka.class,args);
    }

}
