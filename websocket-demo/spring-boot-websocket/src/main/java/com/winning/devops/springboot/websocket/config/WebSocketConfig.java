package com.winning.devops.springboot.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author chensj
 * @title
 * @project spring-boot-websocket
 * @package com.winning.devops.springboot.websocket.config
 * @date: 2019-01-30 14:51
 * @dsecripting WebSocketConfig用@Configuration注释，以表明它是一个Spring配置类。它也被标注为@EnableWebSocketMessageBroker。顾名思义，
 * @dsecripting @EnableWebSocketMessageBroker支持基于消息代理的WebSocket消息处理。
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * The registerStompEndpoints() method registers the "/gs-guide-websocket" endpoint, enabling SockJS fallback options so that alternate transports may be used if WebSocket is not available.
     * The SockJS client will attempt to connect to "/gs-guide-websocket" and use the best transport available (websocket, xhr-streaming, xhr-polling, etc).
     * registerStompEndpoints()方法注册“/gs-guide-websocket”端点，启用SockJS回退选项，以便在WebSocket不可用时可以使用替代传输。
     * SockJS客户端将尝试连接到“/gs-guide-websocket”，并使用可用的最佳传输(websocket、xhr-streaming、xhr-polling等)。
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

    /**
     * The configureMessageBroker() method implements the default method in WebSocketMessageBrokerConfigurer to configure the message broker.
     * It starts by calling enableSimpleBroker() to enable a simple memory-based message broker to carry the greeting messages back to the client on destinations prefixed with "/topic".
     * It also designates the "/app" prefix for messages that are bound for @MessageMapping-annotated methods.
     * This prefix will be used to define all the message mappings;
     * for example, "/app/hello" is the endpoint that the GreetingController.greeting() method is mapped to handle.
     *
     * configureMessageBroker() 实现了来源于WebSocketMessageBrokerConfigurer中配置消息代理的方法的默认方法。
     * 它首先调用enableSimpleBroker()，使一个简单的基于内存的消息代理能够在以“/topic”为前缀的目的地将问候消息带回客户机。
     * 同样也指定了使用 @MessageMapping-annotated注解的方法访问的前缀为“/app”
     * 这个前缀将会用于所有的消息映射中。
     * 比如： "/app/hello"就将会映射到@{com.winning.devops.springboot.websocket.web.GreetingController#greeting}进行处理
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");

    }
}
