package org.chen.spring.security.ssm.controller;

import org.chen.spring.security.ssm.domain.*;
import org.chen.spring.security.ssm.service.Facade;
import org.springframework.beans.factory.annotation.Autowired;
/**
* @author Dev Ops
* @title BaseController
* @email Winning Devops Apollo Client
* @package org.chen.spring.security.ssm.controller.base;
* @date 2019-09-09 22:14:03
*/
public class BaseController {
    @Autowired
    private Facade facade;

    public Facade getFacade() {
        return facade;
    }
}
