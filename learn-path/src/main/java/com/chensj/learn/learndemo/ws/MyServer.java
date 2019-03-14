package com.chensj.learn.learndemo.ws;

import com.chensj.learn.learndemo.ws.service.MyServiceImpl;

import javax.xml.ws.Endpoint;

/**
 * @author chensj
 * @title  服务发布main
 * @email chensj@winning.com.cn
 * @package com.chensj.learn.learndemo.ws
 * @date: 2018-12-16 下午7:27
 */
public class MyServer {

    public static void main(String[] args){
       String address = "http://localhost:6666/ws";
        Endpoint.publish(address,new MyServiceImpl());
    }

}
