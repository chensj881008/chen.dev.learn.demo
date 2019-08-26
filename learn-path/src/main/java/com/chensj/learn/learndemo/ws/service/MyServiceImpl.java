package com.chensj.learn.learndemo.ws.service;

import javax.jws.WebService;

/**
 * @author chensj
 * @title SIB Service Implemntion Bean 服务提供Bean
 * @email chensj@winning.com.cn
 * @package com.chensj.learn.learndemo.ws.service
 * @date: 2018-12-16 下午7:25
 */
@WebService(endpointInterface = "com.chensj.learn.learndemo.ws.service.IMyService")
public class MyServiceImpl implements IMyService {
    @java.lang.Override
    public int add(int a, int b) {
        System.out.println(a + "+" + b + "=" + (a + b));
        return a + b;
    }

    @java.lang.Override
    public int minus(int a, int b) {
        System.out.println(a + "-" + b + "=" + (a - b));
        return a - b;
    }
}
