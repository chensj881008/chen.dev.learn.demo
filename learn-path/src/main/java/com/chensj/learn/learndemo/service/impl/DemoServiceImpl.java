package com.chensj.learn.learndemo.service.impl;

import com.chensj.learn.learndemo.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author chensj
 * @title
 * @package com.chensj.learn.learndemo.service.impl
 * @date: 2018-11-22 12:12
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public void demo() {
        //throw new RuntimeException("测试异常");
        System.out.println("demo service impl");
    }
}
