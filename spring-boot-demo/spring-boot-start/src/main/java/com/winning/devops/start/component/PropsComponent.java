package com.winning.devops.start.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author chensj
 * @title
 * @project spring-boot-start
 * @package .start.component
 * @date: 2019-02-13 11:24
 */
@Component
//@Configuration
//@ConfigurationProperties(prefix = "com.winning.devops")
public class PropsComponent {

    @Value("${com.winning.devops.name}")
    private String name;
    @Value("${com.winning.devops.url}")
    private String url;
    @Value("${com.winning.devops.desc}")
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
