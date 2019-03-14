package com.winning.websocket.server;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author chensj
 * @title
 * @project websoket-web-demo
 * @package com.winning.websocket.server
 * @date: 2019-01-29 8:51
 */
@ServerEndpoint("/webSocket")
public class WebSocket {

    private static String datetime;
    Session session;
    private static CopyOnWriteArrayList<WebSocket> sessions = new CopyOnWriteArrayList<>();
    ScheduledExecutorService servicePool = Executors.newScheduledThreadPool(10);

}
