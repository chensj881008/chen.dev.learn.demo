package com.winning.devops.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author chensj
 * @title 开启WebSocket支持
 * @project websocket-spring-boot
 * @package com.winning.devops.websocket.config
 * @date: 2019-03-07 14:55
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
