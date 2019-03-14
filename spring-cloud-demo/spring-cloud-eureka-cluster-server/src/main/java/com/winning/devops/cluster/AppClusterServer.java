package com.winning.devops.cluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author chensj
 * @title
 * @project spring-cloud-eureka-cluster-server1
 * @package com.winning.devops.cluster
 * @date: 2019-02-04 23:11
 */
@SpringBootApplication
@EnableEurekaServer
public class AppClusterServer {

    public static void main(String[] args){
        SpringApplication.run(AppClusterServer.class,args);
    }

}
