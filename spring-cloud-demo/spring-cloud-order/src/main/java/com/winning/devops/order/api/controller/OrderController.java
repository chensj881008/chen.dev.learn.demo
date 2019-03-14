package com.winning.devops.order.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author chensj
 * @title 订单服务 Controller
 * @project spring-cloud-order
 * @package com.winning.devops.order.api.controller
 * @date: 2019-02-03 22:18
 */
@RestController
public class OrderController {
    /**
     * RestTemplate 是由 spring boot 组件提供
     * 是基于http client，默认整合ribbon负载均衡器
     * rest 方式底层是基于httpClinet技术
     */
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 在Spring Cloud中有两种调用：Rest、feign
     * @return
     */
    @RequestMapping("getOrder")
    public String getOrder(){
        // url 存在两种方式：一种采用服务别名方式调用，另一种是直接调用
        // 采用服务别名方式调用
        String url = "http://app-member/getMember";
        // 上述这种配置将会出现UnknownHostException，无法识别app-member这个服务别名
        // 这个时候需要依赖ribbon的相关jar，需要在RestTemplate注册的时候增加Ribbon的使用
        String result = restTemplate.getForObject(url,String.class);
        // 直接调用
        // String result = restTemplate.getForObject("http://127.0.0.1:8000/getMember",String.class);
        System.out.println("订单服务调用会员服务result:"+result);
        return result;
    }
}
