package com.chensj.learn.learndemo.web;

import com.chensj.learn.learndemo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chensj
 * @title
 * @package com.chensj.learn.learndemo.web
 * @date: 2018-11-22 12:16
 */
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/")
    @ResponseBody
    public String index() {
        demoService.demo();
        return "index";
    }
}
