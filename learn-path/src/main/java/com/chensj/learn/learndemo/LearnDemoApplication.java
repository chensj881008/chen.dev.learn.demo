package com.chensj.learn.learndemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LearnDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnDemoApplication.class, args);
    }
}
