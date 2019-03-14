package com.winning.devops.websocket.logger.system.model;

/**
 * @author chensj
 * @title websocket 消息
 * @project spring-boot-websocket
 * @package com.winning.devops.springboot.websocket.model
 * @date: 2019-01-30 14:45
 */
public class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HelloMessage{" +
                "name='" + name + '\'' +
                '}';
    }
}
