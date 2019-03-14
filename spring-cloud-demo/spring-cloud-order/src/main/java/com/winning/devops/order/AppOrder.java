package com.winning.devops.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author chensj
 * @title
 * @project spring-cloud-order
 * @package com.winning.devops.order
 * @date: 2019-02-03 22:15
 */
@SpringBootApplication
@EnableEurekaClient
public class AppOrder {

    public static void main(String[] args){
        SpringApplication.run(AppOrder.class,args);
    }
    /**
     * 解决RestTemplate找不到的问题
     * 应该将RestTemplate注册到spring 容器中
     * @LoadBalanced 表示能够让RestTemplate拥有客户端负载均衡的能力
     */
    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate(){
        return  new RestTemplate();
    }


}
