package com.winning.devops.websocket.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lenovo
 */
@SpringBootApplication
@EnableScheduling
@RestController
public class SpringBootWebsocketLoggerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SpringBootWebsocketLoggerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebsocketLoggerApplication.class, args);
    }

    int info = 1;
    @Scheduled(fixedRate = 1000)
    public void logger(){
        LOG.info("logger info : "+ ++info);
        throw new RuntimeException("测试信息");
    }

    @RequestMapping("/hello")
    public String hello() {
        LOG.debug("访问了hello");
        return "hello!";
    }
}

