package com.winning.devops.websocket.logger.system.core;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.winning.devops.websocket.logger.system.event.LoggerEvent;
import com.winning.devops.websocket.logger.system.event.LoggerEventFactory;
import com.winning.devops.websocket.logger.system.event.LoggerEventHandler;
import com.winning.devops.websocket.logger.system.model.LoggerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chensj
 * @title Disruptor 环形队列
 * @project spring-boot-websocket-logger
 * @package com.winning.devops.websocket.logger.system.core
 * @date: 2019-01-31 15:29
 */
@Component
public class LoggerDistruptorQueue {
    /**
     * 创建线程池，用于事件处理的线程池
     */
    ExecutorService executor = Executors.newCachedThreadPool();
    /**
     * 创建日志事件工厂
     */
    EventFactory<LoggerEvent> eventFactory = new LoggerEventFactory();
    /**
     * RingBuffer 大小，必须是 2 的 N 次方；
     */
    int ringBufferSize =  1024 * 1024;
    /**
     * 构造器初始化Disruptor
     */
    @SuppressWarnings("deprecation")
    private Disruptor<LoggerEvent> disruptor = new Disruptor<LoggerEvent>(eventFactory, ringBufferSize, executor);
    /**
     * 静态变量 环形的缓冲区
     * 负责对通过 Disruptor 进行交换的数据（事件）进行存储和更新
     */
    private static RingBuffer<LoggerEvent> ringBuffer;

    @Autowired
    LoggerDistruptorQueue(LoggerEventHandler eventHandler){
        System.out.println("init LoggerDistruptorQueue");
        disruptor.handleEventsWith(eventHandler);
        this.ringBuffer = disruptor.getRingBuffer();
        disruptor.start();
    }

    /**
     * 事件发布
     * @param log
     */
    public static void publishEvent(LoggerMessage log) {
        // 请求下一个事件序号；
        long sequence = ringBuffer.next();
        try {
            // //获取该序号对应的事件对象；
            LoggerEvent event = ringBuffer.get(sequence);
            // 设置事件
            event.setMessage(log);  // Fill with data
        } finally {
            // 发布事件；
            ringBuffer.publish(sequence);
        }
    }
}
