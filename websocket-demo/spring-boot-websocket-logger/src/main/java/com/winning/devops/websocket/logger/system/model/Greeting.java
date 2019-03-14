package com.winning.devops.websocket.logger.system.model;

/**
 * @author chensj
 * @title websocket 消息
 * @project spring-boot-websocket
 * @package com.winning.devops.springboot.websocket.model
 * @date: 2019-01-30 14:46
 */
public class Greeting {

    private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "content='" + content + '\'' +
                '}';
    }
}
