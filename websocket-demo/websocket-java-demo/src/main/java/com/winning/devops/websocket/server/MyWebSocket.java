package com.winning.devops.websocket.server;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws")
public class MyWebSocket {

    @OnOpen
    public void onOpen() {
        System.out.println("连接已完成");
    }

    @OnMessage
    public void say(String message, Session session) {
        System.out.println(message);
        try {
            session.getBasicRemote().sendText("我是服务端，你好啊 客户端");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable t) {//参数必须加上，不然不能启动
        System.out.println("error");
    }

    @OnClose
    public void onClose() {
        System.out.println("close");
    }
}
