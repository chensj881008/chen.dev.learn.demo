package com.winning.devops.websocket.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author chensj
 * @title
 * @project websocket-java-demo
 * @package com.winning.devops.websocket.server
 * @date: 2019-01-27 20:52
 */
public class ServerManager {
    private static Collection<BitCoinServer> servers = Collections.synchronizedCollection(new ArrayList<BitCoinServer>());

    public static void broadCast(String msg) {
        for (BitCoinServer bitCoinServer : servers) {
            try {
                bitCoinServer.sendMessage(msg);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static int getTotal() {
        return servers.size();
    }

    public static void add(BitCoinServer server) {
        System.out.println("有新连接加入！ 当前总连接数是：" + servers.size());
        servers.add(server);
    }

    public static void remove(BitCoinServer server) {
        System.out.println("有连接退出！ 当前总连接数是：" + servers.size());
        servers.remove(server);
    }

}
