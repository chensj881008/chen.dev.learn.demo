package com.chensj.learn.learndemo.ws.service;

import javax.jws.WebService;

/**
 * @author chensj
 * @title SEI Serice Endpoint Interface  服务提供借口
 * @email chensj@winning.com.cn
 * @package com.chensj.learn.learndemo.ws.service
 * @date: 2018-12-16 下午7:24
 */
@WebService
public interface IMyService {

    public int add(int a, int b);

    public int minus(int a, int b);
}
