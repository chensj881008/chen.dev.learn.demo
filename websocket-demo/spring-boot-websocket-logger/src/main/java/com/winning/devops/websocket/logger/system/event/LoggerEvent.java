package com.winning.devops.websocket.logger.system.event;

import com.winning.devops.websocket.logger.system.model.LoggerMessage;

/**
 * @author chensj
 * @title 进程日志事件内容载体
 * @project spring-boot-websocket-logger
 * @package com.winning.devops.websocket.logger.system.event
 * @date: 2019-01-31 15:20
 */
public class LoggerEvent {
    /**
     * 事件(Event)就是通过 Disruptor 进行交换的数据类型。
     */
    private LoggerMessage message;

    public LoggerMessage getMessage() {
        return message;
    }

    public void setMessage(LoggerMessage message) {
        this.message = message;
    }
}
