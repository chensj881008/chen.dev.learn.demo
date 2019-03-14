package com.winning.devops.websocket.logger.system.event;

import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author chensj
 * @title  进程日志事件处理器
 * @project spring-boot-websocket-logger
 * @package com.winning.devops.websocket.logger.system.event
 * @date: 2019-01-31 15:24
 * @descript  定义事件处理的具体实现
 */
@Component
public class LoggerEventHandler implements EventHandler<LoggerEvent> {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 通过实现接口 com.lmax.disruptor.EventHandler<T> 定义事件处理的具体实现。
     * @throws Exception
     */
    @Override
    public void onEvent(LoggerEvent loggerEvent, long l, boolean b) throws Exception {
        // 日志推送到/topic/pulllogger中
        simpMessagingTemplate.convertAndSend("/topic/pullLogger",loggerEvent.getMessage());
    }
}
