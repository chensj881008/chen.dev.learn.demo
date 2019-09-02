package org.chen.spring.security4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chensj
 * @date 2019-08-28 19:41
 */
@Controller
public class RootIndexController {

    @RequestMapping(value = {"/", "/index"})
    public String rootIndex() {
        return "index";
    }

    @RequestMapping(value = {"/userLogin"})
    public String loginIndex() {
        return "login";
    }
}
