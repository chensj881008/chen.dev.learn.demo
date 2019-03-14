package com.winning.devops.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chensj
 * @title spring eureka 客户端
 * @project spring-cloud-member
 * @package com.winning.devops.member
 * @date: 2019-02-03 21:58
 */
@SpringBootApplication
@EnableEurekaClient
public class AppMember {

    // @EnableEurekaClient 将当前服务到Eureka中

    public static void main(String[] args){
        SpringApplication.run(AppMember.class,args);
    }

}
