package com.winning.devops.websocket.logger.system.event;

import com.lmax.disruptor.EventFactory;

/**
 * @author chensj
 * @title 进程日志事件工厂类
 * @project spring-boot-websocket-logger
 * @package com.winning.devops.websocket.logger.system.event
 * @date: 2019-01-31 15:22
 * 事件工厂(Event Factory)定义了如何实例化前面第1步中定义的事件(Event)，
 * 需要实现接口 com.lmax.disruptor.EventFactory<T>。
 * Disruptor 通过 EventFactory 在 RingBuffer 中预创建 Event 的实例。
 */
public class LoggerEventFactory implements EventFactory<LoggerEvent> {

    @Override
    public LoggerEvent newInstance() {
        return new LoggerEvent();
    }
}
