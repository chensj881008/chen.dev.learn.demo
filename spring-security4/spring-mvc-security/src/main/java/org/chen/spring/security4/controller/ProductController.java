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


    /**
     * 商品新增 调整product/productAdd页面
     *
     * @return str
     */
    @RequestMapping(value = "add")
    public String add() {
        return "product/productAdd";
    }

    /**
     * 商品删除 调整product/productDelete页面
     *
     * @return str
     */
    @RequestMapping(value = "delete")
    public String delete() {
        return "product/productDelete";
    }

    /**
     * 商品修改 调整product/productUpdate页面
     *
     * @return str
     */
    @RequestMapping(value = "update")
    public String update() {
        return "product/productUpdate";
    }

    /**
     * 商品查询 调整product/productList页面
     *
     * @return str
     */
    @RequestMapping(value = "list")
    public String list() {
        return "product/productList";
    }
}
