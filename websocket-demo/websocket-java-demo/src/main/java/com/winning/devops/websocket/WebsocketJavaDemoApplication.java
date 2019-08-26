package com.winning.devops.websocket;

import com.alibaba.fastjson.JSONObject;
import com.winning.devops.websocket.utils.WebSocket;

import java.io.IOException;

/**
 * @author chensj
 * @title
 * @project websocket-java-demo
 * @package com.winning.devops.websocket
 * @date: 2019-01-25 14:36
 */
public class WebsocketJavaDemoApplication {

    public static void main(String[] args) {
        WebSocket ws = new WebSocket();
        JSONObject jo = new JSONObject();
        jo.put("message", "这是后台返回的消息！");
        jo.put("To", "ALL");
        try {
            ws.onMessage(jo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
