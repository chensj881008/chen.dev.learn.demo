package org.chen.spring.security4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 产品 控制器
 *
 * @author chensj
 * @date 2019-08-28 17:15
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {


    @RequestMapping(value = "add")
    public String add() {
        return "product/productAdd";
    }

    @RequestMapping(value = "delete")
    public String delete() {
        return "product/productDelete";
    }

    @RequestMapping(value = "update")
    public String update() {
        return "product/productUpdate";
    }

    @RequestMapping(value = "list")
    public String list() {
        return "product/productList";
    }
}
