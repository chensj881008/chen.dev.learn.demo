package com.winning.devops.start.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chensj
 * @title
 * @project spring-boot-start
 * @package com.winning.devops.start.web
 * @date: 2019-02-13 15:42
 */
@RestController
@RequestMapping("hello")
public class HelloController {
    /**
     * 通过@ApiOperation注解来给API增加说明、
     * 通过@ApiImplicitParams、@ApiImplicitParam注解来给参数增加说明
     * @return str
     */
    @ApiOperation(value = "sayHello",notes = "")
    @ApiImplicitParam()
    @GetMapping(value = {""})
    public String sayHelloPage(){
        return "hello";
    }
}
