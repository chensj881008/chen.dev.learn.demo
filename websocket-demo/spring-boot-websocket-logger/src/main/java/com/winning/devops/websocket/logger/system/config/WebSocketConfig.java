package com.winning.devops.websocket.logger.system.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author chensj
 * @title WebSocketConfig配置
 * @project spring-boot-websocket-logger
 * @package com.winning.devops.websocket.logger.system.config
 * @date: 2019-01-30 19:56
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     *  该registerStompEndpoints()方法注册“/logger”端点，
     *  启用SockJS后备选项，以便在WebSocket不可用时可以使用替代传输。
     *  SockJS客户端将尝试连接到“/logger”
     *  并使用可用的最佳传输（websocket，xhr-streaming，xhr-polling等）。
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/logger").setAllowedOrigins("*").addInterceptors().withSockJS();
    }

    /**
     *  该configureMessageBroker()方法将重写方法WebSocketMessageBrokerConfigurer来配置消息代理。
     *  它首先调用enableSimpleBroker()一个简单的基于内存的消息代理，将问候消息带回以“/stock”为前缀的客户端。
     *  它还指定了为“ @MessageMapping注解”方法绑定的消息的“/app”前缀。这个前缀将被用来定义所有的消息映射;
     *  例如，“/app/hello”是该GreetingController.greeting()方法被映射为处理的端点。
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

}
