package com.winning.devops.member.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chensj
 * @title 会员服务Controller
 * @project spring-cloud-member
 * @package com.winning.devops.member.api.controller
 * @date: 2019-02-03 22:03
 */
@RestController
public class MemberApiController {
    @Value("${server.port}")
    private String serverPort;
    /**
     *  获取会员
     * @return
     */
    @RequestMapping(value = "/getMember")
    public String getMember(){
        return "this is member , come from class: "+getClass()+", port:" + serverPort;
    }
}
